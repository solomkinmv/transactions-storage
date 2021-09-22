package io.github.solomkinmv.transactions.service.validate;

import io.github.solomkinmv.transactions.controller.dto.CardholderRequest;
import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import org.springframework.stereotype.Service;

/*
Requires complex logic. Better to use external lib (and test that external lib)
 */
@Service
class EmailValidator implements Validator {

    @Override
    public String validate(SubmitTransactionRequest transactionRequest) {
        CardholderRequest cardholder = transactionRequest.cardholder();
        if (cardholder == null) {
            return "Email should be present";
        }
        String email = cardholder.email();
        if (email == null) {
            return "Email should be present";
        }
        if (!org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email)) {
            return "Email should be valid";
        }
        return null;
    }
}
