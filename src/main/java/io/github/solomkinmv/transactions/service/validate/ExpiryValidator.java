package io.github.solomkinmv.transactions.service.validate;

import io.github.solomkinmv.transactions.controller.dto.CardRequest;
import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;

@Service
@AllArgsConstructor
class ExpiryValidator implements Validator {

    private final Clock clock;

    @Override
    public String validate(SubmitTransactionRequest transactionRequest) {
        CardRequest card = transactionRequest.card();
        if (card == null) {
            return "Expiry should be present";
        }
        String expiry = card.expiry();
        if (expiry == null) {
            return "Expiry should be present";
        }
        if (expiry.length() != 4) {
            return "Expiry should have length 4 and have format MMYY";
        }
        if (!isDigit(expiry, 0) || !isDigit(expiry, 1) ||
                !isDigit(expiry, 2) || !isDigit(expiry, 3)) {
            return "Expiry should contain only digits and have format MMYY";
        }
        int month = (expiry.charAt(0) - '0') * 10 + (expiry.charAt(1) - '0');
        if (month < 1 || month > 12) {
            return "Expiration month should be between 1 and 12";
        }
        int year = (expiry.charAt(2) - '0') * 10 + (expiry.charAt(3) - '0');
        LocalDate cardExpirationDate = LocalDate.of(2000 + year, month, 1).plusMonths(1);
        LocalDate currentDate = LocalDate.now(clock);
        if (currentDate.compareTo(cardExpirationDate) > 0) {
            return "Expiration date should be in future";
        }
        return null;
    }

    private boolean isDigit(String expiry, int charIndex) {
        return Character.isDigit(expiry.charAt(charIndex));
    }

}
