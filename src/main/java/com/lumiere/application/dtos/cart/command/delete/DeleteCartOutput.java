package com.lumiere.application.dtos.cart.command.delete;

public record DeleteCartOutput(String message) {
    public DeleteCartOutput() {
        this("Cart deleted successfully!");
    }
}
