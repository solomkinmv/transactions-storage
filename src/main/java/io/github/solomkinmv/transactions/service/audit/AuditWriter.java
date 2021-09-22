package io.github.solomkinmv.transactions.service.audit;

import io.github.solomkinmv.transactions.persistence.model.Transaction;

public interface AuditWriter {

    // maybe audit all requests, but in that case private data may be written to unsecured file
    void onSubmit(Transaction transaction);
}
