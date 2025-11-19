package com.lumiere.domain.vo;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import com.lumiere.domain.vo.base.ValueObject;

public class CartItem extends ValueObject {

    private final UUID productId;
    private final int quantity;

    public CartItem(UUID productId, int quantity) {
        this.productId = Objects.requireNonNull(productId, "Product ID cannot be null");
        this.quantity = quantity;
        validate();
    }

    @Override
    protected Stream<Object> getAtomicValues() {
        return Stream.of(productId, quantity);
    }

    @Override
    protected void validate() {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
    }

    public UUID getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public CartItem withQuantity(int newQuantity) {
        return new CartItem(this.productId, newQuantity);
    }
}