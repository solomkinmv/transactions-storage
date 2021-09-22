package io.github.solomkinmv.transactions.service.validate;

import io.github.solomkinmv.transactions.controller.dto.CardRequest;
import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import org.springframework.stereotype.Service;

/*
 Sources:
 1. https://en.wikipedia.org/wiki/Luhn_algorithm
 2. https://ru.wikipedia.org/wiki/Алгоритм_Луна
 Requires tests to verify algorithm
 */
@Service
class PanValidator implements Validator {

    @Override
    public String validate(SubmitTransactionRequest transactionRequest) {
        CardRequest card = transactionRequest.card();
        if (card == null) {
            return "PAN should be present";
        }
        String pan = card.pan();
        if (pan == null) {
            return "PAN should be present";
        }
        int nDigits = pan.length();
        if (nDigits != 16) {
            return "PAN should have length 16";
        }
        int sum1 = 0, sum2 = 0;
        for (int i = nDigits; i > 0; i--) {
            int digit = Character.getNumericValue(pan.charAt(i - 1));
            if (i % 2 == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum2 += digit;
        }
        if ((sum1 + sum2) % 10 != 0) {
            return "PAN should pass a Luhn check";
        }
        return null;
    }

}
