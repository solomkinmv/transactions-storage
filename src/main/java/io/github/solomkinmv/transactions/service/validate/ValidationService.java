package io.github.solomkinmv.transactions.service.validate;

import io.github.solomkinmv.transactions.controller.dto.CardErrorResponse;
import io.github.solomkinmv.transactions.controller.dto.CardholderErrorResponse;
import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import io.github.solomkinmv.transactions.controller.dto.TransactionErrorResponse;
import io.github.solomkinmv.transactions.service.converter.TransactionConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ValidationService {

    private final InvoiceValidator invoiceValidator;
    private final AmountValidator amountValidator;
    private final CurrencyValidator currencyValidator;
    private final NameValidator nameValidator;
    private final EmailValidator emailValidator;
    private final PanValidator panValidator;
    private final ExpiryValidator expiryValidator;
    private final CvvValidator cvvValidator;
    private final TransactionConverter converter;

    public ValidationResult validate(SubmitTransactionRequest transactionRequest) {
        CardholderErrorResponse cardholderErrorResponse = new CardholderErrorResponse(
                nameValidator.validate(transactionRequest),
                emailValidator.validate(transactionRequest));
        CardErrorResponse cardErrorResponse = new CardErrorResponse(panValidator.validate(transactionRequest),
                                                                    expiryValidator.validate(transactionRequest),
                                                                    cvvValidator.validate(transactionRequest));
        TransactionErrorResponse transactionErrorResponse =
                new TransactionErrorResponse(invoiceValidator.validate(transactionRequest),
                                             amountValidator.validate(transactionRequest),
                                             currencyValidator.validate(transactionRequest),
                                             cardholderErrorResponse.hasErrors() ? cardholderErrorResponse : null,
                                             cardErrorResponse.hasErrors() ? cardErrorResponse : null);
        if (transactionErrorResponse.hasErrors()) {
            return new ValidationResult(null, transactionErrorResponse);
        }
        return new ValidationResult(converter.convert(transactionRequest), null);
    }
}
