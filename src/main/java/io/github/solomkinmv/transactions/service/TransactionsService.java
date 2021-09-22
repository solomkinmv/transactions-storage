package io.github.solomkinmv.transactions.service;

import io.github.solomkinmv.transactions.controller.dto.SanitizedTransactionResponse;
import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import io.github.solomkinmv.transactions.controller.dto.TransactionErrorResponse;
import io.github.solomkinmv.transactions.persistence.TransactionsRepository;
import io.github.solomkinmv.transactions.service.converter.TransactionSanitizer;
import io.github.solomkinmv.transactions.service.validate.ValidationResult;
import io.github.solomkinmv.transactions.service.validate.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionsService {

    private final TransactionsRepository repository;
    private final ValidationService validator;
    private final TransactionSanitizer transactionSanitizer;

    public Optional<TransactionErrorResponse> submitTransaction(SubmitTransactionRequest transactionRequest) {
        ValidationResult validationResult = validator.validate(transactionRequest);
        if (validationResult.errorResponse() != null) {
            return Optional.of(validationResult.errorResponse());
        }
        repository.save(validationResult.validTransaction());
        return Optional.empty();
    }

    public Optional<SanitizedTransactionResponse> getTransaction(int invoiceId) {
        return repository.findByInvoice(invoiceId)
                         .map(transactionSanitizer::convert);
    }
}
