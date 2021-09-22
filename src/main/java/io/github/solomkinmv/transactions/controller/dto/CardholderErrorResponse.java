package io.github.solomkinmv.transactions.controller.dto;

public record CardholderErrorResponse(String name, String email) {

    public boolean hasErrors() {
        return name != null || email != null;
    }
}
