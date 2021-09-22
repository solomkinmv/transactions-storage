package io.github.solomkinmv.transactions.service;

import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import io.github.solomkinmv.transactions.controller.dto.TransactionErrorResponse;
import io.github.solomkinmv.transactions.persistence.TransactionsRepository;
import io.github.solomkinmv.transactions.persistence.model.Transaction;
import io.github.solomkinmv.transactions.service.validate.ValidationResult;
import io.github.solomkinmv.transactions.service.validate.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionsServiceTest {

    private static final SubmitTransactionRequest TRANSACTION_REQUEST =
            new SubmitTransactionRequest(2, null, null, null, null);
    private static final TransactionErrorResponse TRANSACTION_ERROR_RESPONSE =
            new TransactionErrorResponse("some error msg", null, null, null, null);
    private static final Transaction VALID_TRANSACTION = new Transaction(2, 3, null, null, null);
    private TransactionsService transactionsService;
    @Mock
    private TransactionsRepository repository;
    @Mock
    private ValidationService validator;

    @BeforeEach
    void setUp() {
        transactionsService = new TransactionsService(repository, validator);
    }

    @Test
    void returnsValidationErrorIfValidationFailed() {
        when(validator.validate(TRANSACTION_REQUEST))
                .thenReturn(new ValidationResult(null, TRANSACTION_ERROR_RESPONSE));
        Optional<TransactionErrorResponse> actualResult = transactionsService.submitTransaction(TRANSACTION_REQUEST);

        assertThat(actualResult)
                .contains(TRANSACTION_ERROR_RESPONSE);
        verifyNoInteractions(repository);
    }

    @Test
    void savedValidTransactionIfValidationSuccessful() {
        when(validator.validate(TRANSACTION_REQUEST))
                .thenReturn(new ValidationResult(VALID_TRANSACTION, null));
        Optional<TransactionErrorResponse> actualResult = transactionsService.submitTransaction(TRANSACTION_REQUEST);

        assertThat(actualResult)
                .isEmpty();
        verify(repository).save(VALID_TRANSACTION);
    }
}