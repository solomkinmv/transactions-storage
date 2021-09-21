package io.github.solomkinmv.transactions.persistence;

import io.github.solomkinmv.transactions.persistence.model.Card;
import io.github.solomkinmv.transactions.persistence.model.Cardholder;
import io.github.solomkinmv.transactions.persistence.model.Transaction;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryTransactionsRepositoryTest {
    private final InMemoryTransactionsRepository repository = new InMemoryTransactionsRepository();

    @Test
    void returnsEmptyOptionalIfNoTransaction() {
        Optional<Transaction> actualResult = repository.findByInvoice(123);

        assertThat(actualResult).isEmpty();
    }

    @Test
    void savesAndRetrievesTransaction() {
        int invoice = 123;
        Transaction originalTransaction = new Transaction(invoice, 321, "EUR",
                                                          new Cardholder("First Last", "email"),
                                                          new Card("123124", "2134"));

        repository.save(originalTransaction);
        Optional<Transaction> actualResult = repository.findByInvoice(invoice);

        assertThat(actualResult)
                .contains(originalTransaction);
    }
}