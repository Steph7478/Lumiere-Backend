package com.lumiere.domain.readmodels;

public record CartItemReadModel(
        ProductDetailReadModel product,
        int quantity) {
}
