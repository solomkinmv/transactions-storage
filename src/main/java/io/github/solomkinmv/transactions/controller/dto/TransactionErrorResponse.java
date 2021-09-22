package io.github.solomkinmv.transactions.controller.dto;

public record TransactionErrorResponse(String invoice, String amount, String currency,
                                       CardholderErrorResponse cardholder, CardErrorResponse card) {
    public boolean hasErrors() {
        return invoice != null || amount != null || currency != null || cardholder().hasErrors() || card.hasErrors();
    }
}
