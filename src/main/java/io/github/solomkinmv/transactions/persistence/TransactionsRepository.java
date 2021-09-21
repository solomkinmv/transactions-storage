package io.github.solomkinmv.transactions.persistence;

import io.github.solomkinmv.transactions.persistence.model.Transaction;

import java.util.Optional;

public interface TransactionsRepository {
    void save(Transaction transaction);
    Optional<Transaction> findByInvoice(int invoice);
}
