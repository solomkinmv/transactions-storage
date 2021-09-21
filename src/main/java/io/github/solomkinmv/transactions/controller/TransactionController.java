package io.github.solomkinmv.transactions.controller;

import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @PostMapping
    public SubmitTransactionResponse submitTransaction(SubmitTransactionRequest request) {
        return SubmitTransactionResponse.approvedResponse();
    }
}
