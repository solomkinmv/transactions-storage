package io.github.solomkinmv.transactions.service.converter;

import io.github.solomkinmv.transactions.controller.dto.SanitizedCardResponse;
import io.github.solomkinmv.transactions.controller.dto.SanitizedCardholderResponse;
import io.github.solomkinmv.transactions.controller.dto.SanitizedTransactionResponse;
import io.github.solomkinmv.transactions.persistence.model.Transaction;
import io.github.solomkinmv.transactions.service.encrypt.EncryptionService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionSanitizer implements Converter<Transaction, SanitizedTransactionResponse> {

    private final EncryptionService encryptionService;

    @Override
    public SanitizedTransactionResponse convert(Transaction source) {
        String decryptedPan = encryptionService.decrypt(source.card().pan()).substring(12, 16);
        return new SanitizedTransactionResponse(
                source.invoice(), source.amount(), source.currency(),
                new SanitizedCardholderResponse("**********", source.cardholder().email()),
                new SanitizedCardResponse("************" + decryptedPan, "****"));
    }
}
