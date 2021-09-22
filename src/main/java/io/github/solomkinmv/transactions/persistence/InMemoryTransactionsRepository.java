package io.github.solomkinmv.transactions.persistence;

import io.github.solomkinmv.transactions.persistence.model.Transaction;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
//@ConditionalOnMissingBean(TransactionsRepository.class) // todo: test autoconfiguration with context test
public class InMemoryTransactionsRepository implements TransactionsRepository {
    private final ConcurrentHashMap<Integer, Transaction> hashMap = new ConcurrentHashMap<>();

    @Override
    public void save(Transaction transaction) {
        hashMap.put(transaction.invoice(), transaction);
    }

    @Override
    public Optional<Transaction> findByInvoice(int invoice) {
        return Optional.ofNullable(hashMap.get(invoice));
    }
}
