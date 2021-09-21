package io.github.solomkinmv.transactions.controller.dto;

public record SubmitTransactionRequest(Integer invoice, Integer amount, String currency,
                                       CardholderRequest cardholder, CardRequest card) {
}
