package io.github.solomkinmv.transactions.service.validate;

import io.github.solomkinmv.transactions.controller.dto.CardRequest;
import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import org.springframework.stereotype.Service;

@Service
class CvvValidator implements Validator {

    @Override
    public String validate(SubmitTransactionRequest transactionRequest) {
        CardRequest card = transactionRequest.card();
        if (card == null) {
            return "CVV should be present";
        }
        String cvv = card.cvv();
        if (cvv == null) {
            return "CVV should be present";
        }
        if (cvv.length() != 3) {
            return "CVV should have length 3";
        }
        if (!isDigit(cvv, 0) || !isDigit(cvv, 1) || !isDigit(cvv, 2)) {
            return "CVV should contain only digits";
        }
        return null;
    }

    private boolean isDigit(String expiry, int charIndex) {
        return Character.isDigit(expiry.charAt(charIndex));
    }
}
