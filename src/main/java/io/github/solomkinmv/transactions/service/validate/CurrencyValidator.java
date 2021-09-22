package io.github.solomkinmv.transactions.service.validate;

import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
class CurrencyValidator implements Validator {

    // better to use configuration file for this
    private static final Set<String> VALID_CURRENCIES = Set.of("USD", "EUR", "UAH");

    @Override
    public String validate(SubmitTransactionRequest transactionRequest) {
        String currency = transactionRequest.currency();
        if (currency == null) {
            return "Currency should be present";
        }
        if (!VALID_CURRENCIES.contains(currency)) {
            return "Currency should be valid";
        }
        return null;
    }
}
