package io.github.solomkinmv.transactions.controller.dto;

public record SubmitTransactionResponse(boolean approved, TransactionErrorResponse errors) {

    private static final SubmitTransactionResponse APPROVED_TRANSACTION_RESPONSE =
            new SubmitTransactionResponse(true, null);

    public static SubmitTransactionResponse approvedResponse() {
        return APPROVED_TRANSACTION_RESPONSE;
    }

    public static SubmitTransactionResponse failedResponse(TransactionErrorResponse transactionErrorResponse) {
        return new SubmitTransactionResponse(false, transactionErrorResponse);
    }
}
