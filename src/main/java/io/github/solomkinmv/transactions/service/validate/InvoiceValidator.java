package io.github.solomkinmv.transactions.service.validate;

import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import org.springframework.stereotype.Service;

@Service
class InvoiceValidator implements Validator {

    public String validate(SubmitTransactionRequest transactionRequest) {
        if (transactionRequest.invoice() == null) {
            return "Invoice should be present";
        }
        return null;
    }
}
