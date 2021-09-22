package io.github.solomkinmv.transactions.service.converter;

import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import io.github.solomkinmv.transactions.persistence.model.Card;
import io.github.solomkinmv.transactions.persistence.model.Cardholder;
import io.github.solomkinmv.transactions.persistence.model.Transaction;
import io.github.solomkinmv.transactions.service.encrypt.EncryptionService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

/**
 * Should be invoked only after request validation
 */
@Service
@AllArgsConstructor
public class TransactionConverter implements Converter<SubmitTransactionRequest, Transaction> {

    private final EncryptionService encryptionService;

    @Override
    public Transaction convert(SubmitTransactionRequest source) {
        return new Transaction(source.invoice(), source.amount(), source.currency(),
                               new Cardholder(encryptionService.encrypt(source.cardholder().name()),
                                              source.cardholder().email()),
                               new Card(encryptionService.encrypt(source.card().pan()),
                                        encryptionService.encrypt(source.card().expiry())));
    }
}
