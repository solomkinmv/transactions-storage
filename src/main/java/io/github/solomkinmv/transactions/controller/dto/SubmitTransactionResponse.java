package io.github.solomkinmv.transactions.controller.dto;

public record SubmitTransactionResponse(boolean approved, ErrorResponse errorResponse) {
    public static SubmitTransactionResponse approvedResponse() {
        return new SubmitTransactionResponse(true, null);
    }
}
