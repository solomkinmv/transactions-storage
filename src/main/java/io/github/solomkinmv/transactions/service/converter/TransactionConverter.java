package io.github.solomkinmv.transactions.service.converter;

import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import io.github.solomkinmv.transactions.persistence.model.Card;
import io.github.solomkinmv.transactions.persistence.model.Cardholder;
import io.github.solomkinmv.transactions.persistence.model.Transaction;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

/**
 * Should be invoked only after request validation
 */
@Service
public class TransactionConverter implements Converter<SubmitTransactionRequest, Transaction> {

    @Override
    public Transaction convert(SubmitTransactionRequest source) {
        return new Transaction(source.invoice(), source.amount(), source.currency(),
                               new Cardholder(source.cardholder().name(), source.cardholder().email()),
                               new Card(source.card().pan(), source.card().expiry()));
    }
}
