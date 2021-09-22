package io.github.solomkinmv.transactions.service.validate;

import io.github.solomkinmv.transactions.controller.dto.CardholderRequest;
import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import org.springframework.stereotype.Service;

@Service
class NameValidator implements Validator {
    @Override
    public String validate(SubmitTransactionRequest transactionRequest) {
        CardholderRequest cardholder = transactionRequest.cardholder();
        if (cardholder == null) {
            return "Name should be present";
        }
        String name = cardholder.name();
        if (name == null) {
            return "Name should be present";
        }
        if (name.isBlank()) {
            return "Name should not be blank";
        }
        // everything else can happen in real life ¯ \ _ (ツ) _ / ¯
        return null;
    }
}
