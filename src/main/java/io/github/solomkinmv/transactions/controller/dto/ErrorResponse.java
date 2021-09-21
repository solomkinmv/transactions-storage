package io.github.solomkinmv.transactions.controller.dto;

public record ErrorResponse(String invoice, String amount, String currency, String email, String expiry, String pan) {
}
