package io.github.solomkinmv.transactions.service.validate;

import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import org.springframework.stereotype.Service;

@Service
class AmountValidator implements Validator {

    @Override
    public String validate(SubmitTransactionRequest transactionRequest) {
        if (transactionRequest.amount() == null) {
            return "Amount should be present";
        }
        if (transactionRequest.amount() <= 0) {
            return "Amount should be positive integer";
        }
        return null;
    }
}
