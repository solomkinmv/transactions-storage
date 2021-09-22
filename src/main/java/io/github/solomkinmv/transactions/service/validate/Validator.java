package io.github.solomkinmv.transactions.service.validate;

import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import org.springframework.lang.Nullable;

public interface Validator {
    @Nullable
    String validate(SubmitTransactionRequest transactionRequest);
}
