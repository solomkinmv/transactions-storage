package io.github.solomkinmv.transactions.service.converter;

import io.github.solomkinmv.transactions.controller.dto.SanitizedCardResponse;
import io.github.solomkinmv.transactions.controller.dto.SanitizedCardholderResponse;
import io.github.solomkinmv.transactions.controller.dto.SanitizedTransactionResponse;
import io.github.solomkinmv.transactions.persistence.model.Transaction;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class TransactionSanitizer implements Converter<Transaction, SanitizedTransactionResponse> {

    @Override
    public SanitizedTransactionResponse convert(Transaction source) {
        return new SanitizedTransactionResponse(
                source.invoice(), source.amount(), source.currency(),
                new SanitizedCardholderResponse("**********", source.cardholder().email()),
                new SanitizedCardResponse("************" + source.card().pan().substring(12, 16), "****"));
    }
}
