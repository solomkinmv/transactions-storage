package io.github.solomkinmv.transactions.persistence.model;

public record Transaction(int invoice, int amount, String currency, Cardholder cardholder, Card card) {
}
