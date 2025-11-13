package com.lumiere.domain.vo;

import java.util.stream.Stream;
import com.lumiere.domain.vo.base.ValueObject;

public final class Stock extends ValueObject {

    private final int quantity;

    public Stock(int quantity) {
        validate(quantity);
        this.quantity = quantity;
    }

    @Override
    protected Stream<Object> getAtomicValues() {
        return Stream.of(quantity);
    }

    private void validate(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public Stock add(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Quantity to add must be positive");
        }
        return new Stock(this.quantity + amount);
    }

    public Stock subtract(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Quantity to subtract must be positive");
        }
        int newQuantity = this.quantity - amount;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Resulting stock cannot be negative");
        }
        return new Stock(newQuantity);
    }

    @Override
    public String toString() {
        return String.valueOf(quantity);
    }
}
