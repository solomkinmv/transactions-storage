package io.github.solomkinmv.transactions.controller.dto;

public record CardErrorResponse(String pan, String expiry, String cvv) {

    public boolean hasErrors() {
        return pan != null || expiry != null || cvv != null;
    }
}
