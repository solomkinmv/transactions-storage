package io.github.solomkinmv.transactions.service.validate;

import io.github.solomkinmv.transactions.controller.dto.TransactionErrorResponse;
import io.github.solomkinmv.transactions.persistence.model.Transaction;

public record ValidationResult(Transaction validTransaction, TransactionErrorResponse errorResponse) {
    // todo: constructor private, 2 static factory methods
}
