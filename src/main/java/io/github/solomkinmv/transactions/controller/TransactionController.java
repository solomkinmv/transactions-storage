package io.github.solomkinmv.transactions.controller;

import io.github.solomkinmv.transactions.controller.dto.SanitizedTransactionResponse;
import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionRequest;
import io.github.solomkinmv.transactions.controller.dto.SubmitTransactionResponse;
import io.github.solomkinmv.transactions.service.TransactionsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/transactions", produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor
// todo: add controller advice
public class TransactionController {

    private static final ResponseEntity<SubmitTransactionResponse> SUCCESSFUL_SUBMIT_RESPONSE =
            ResponseEntity.ok(SubmitTransactionResponse.approvedResponse());
    private final TransactionsService transactionsService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<SubmitTransactionResponse> submitPayment(@RequestBody SubmitTransactionRequest request) {
        return transactionsService.submitTransaction(request)
                                  .map(errorResponse ->
                                               ResponseEntity.badRequest()
                                                             .body(SubmitTransactionResponse.failedResponse(errorResponse)))
                                  .orElse(SUCCESSFUL_SUBMIT_RESPONSE);
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<SanitizedTransactionResponse> getTransaction(@PathVariable int invoiceId) {
        return ResponseEntity.of(transactionsService.getTransaction(invoiceId));
    }
}
