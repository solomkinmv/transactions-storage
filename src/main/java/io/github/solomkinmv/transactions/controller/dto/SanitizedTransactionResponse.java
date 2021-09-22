package io.github.solomkinmv.transactions.controller.dto;

public record SanitizedTransactionResponse(int invoice, int amount, String currency,
                                           SanitizedCardholderResponse cardholder, SanitizedCardResponse card) {
}
