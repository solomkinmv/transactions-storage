package io.github.solomkinmv.transactions.controller.dto;

public record CardRequest(String pan, String expiry, String cvv) {
}
