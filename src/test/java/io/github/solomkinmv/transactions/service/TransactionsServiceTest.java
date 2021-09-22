package io.github.solomkinmv.transactions.service;

import io.github.solomkinmv.transactions.controller.dto.SanitizedTransactionResponse;
import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import io.github.solomkinmv.transactions.controller.dto.TransactionErrorResponse;
import io.github.solomkinmv.transactions.persistence.TransactionsRepository;
import io.github.solomkinmv.transactions.persistence.model.Transaction;
import io.github.solomkinmv.transactions.service.audit.AuditWriter;
import io.github.solomkinmv.transactions.service.converter.TransactionSanitizer;
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

    private static final int INVOICE = 2;
    private static final SanitizedTransactionResponse SANITIZED_TRANSACTION_RESPONSE = new SanitizedTransactionResponse(INVOICE, 3, null, null, null);
    private static final SubmitTransactionRequest TRANSACTION_REQUEST =
            new SubmitTransactionRequest(INVOICE, null, null, null, null);
    private static final TransactionErrorResponse TRANSACTION_ERROR_RESPONSE =
            new TransactionErrorResponse("some error msg", null, null, null, null);
    private static final Transaction VALID_TRANSACTION = new Transaction(2, 3, null, null, null);
    private TransactionsService transactionsService;
    @Mock
    private TransactionsRepository repository;
    @Mock
    private ValidationService validationService;
    @Mock
    private TransactionSanitizer transactionSanitizer;
    @Mock
    private AuditWriter auditWriter;

    @BeforeEach
    void setUp() {
        transactionsService = new TransactionsService(repository, validationService,
                                                      transactionSanitizer, auditWriter);
    }

    @Test
    void returnsValidationErrorIfValidationFailed() {
        when(validationService.validate(TRANSACTION_REQUEST))
                .thenReturn(new ValidationResult(null, TRANSACTION_ERROR_RESPONSE));

        Optional<TransactionErrorResponse> actualResult = transactionsService.submitTransaction(TRANSACTION_REQUEST);

        assertThat(actualResult)
                .contains(TRANSACTION_ERROR_RESPONSE);
        verifyNoInteractions(repository);
        verifyNoInteractions(transactionSanitizer);
        verifyNoInteractions(auditWriter);
    }

    @Test
    void savesValidTransactionIfValidationSuccessful() {
        when(validationService.validate(TRANSACTION_REQUEST))
                .thenReturn(new ValidationResult(VALID_TRANSACTION, null));

        Optional<TransactionErrorResponse> actualResult = transactionsService.submitTransaction(TRANSACTION_REQUEST);

        assertThat(actualResult)
                .isEmpty();
        verify(repository).save(VALID_TRANSACTION);
        verify(auditWriter).onSubmit(VALID_TRANSACTION);
        verifyNoInteractions(transactionSanitizer);
    }

    @Test
    void returnsSanitizedTransactionIfPresent() {
        when(repository.findByInvoice(INVOICE))
                .thenReturn(Optional.of(VALID_TRANSACTION));
        when(transactionSanitizer.convert(VALID_TRANSACTION))
                .thenReturn(SANITIZED_TRANSACTION_RESPONSE);

        Optional<SanitizedTransactionResponse> actualResult = transactionsService.getTransaction(INVOICE);

        assertThat(actualResult)
                .contains(SANITIZED_TRANSACTION_RESPONSE);
        verifyNoInteractions(validationService);
        verifyNoInteractions(auditWriter);
    }

    @Test
    void returnsEmptyOptionalIfNoSuchTransaction() {
        when(repository.findByInvoice(INVOICE))
                .thenReturn(Optional.empty());

        Optional<SanitizedTransactionResponse> actualResult = transactionsService.getTransaction(INVOICE);

        assertThat(actualResult).isEmpty();
        verifyNoInteractions(transactionSanitizer);
        verifyNoInteractions(validationService);
        verifyNoInteractions(auditWriter);
    }
}