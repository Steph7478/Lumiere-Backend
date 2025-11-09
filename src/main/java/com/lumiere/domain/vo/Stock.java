package com.lumiere.domain.vo;

import java.util.stream.Stream;

import com.lumiere.domain.vo.base.ValueObject;

public class Stock extends ValueObject {
    public final Integer stock;

    public Stock(Integer stock) {
        this.stock = stock;
        validate();
    }

    @Override
    protected Stream<Object> getAtomicValues() {
        return Stream.of(stock);
    }

    @Override
    protected void validate() {
        if (stock == null)
            throw new IllegalArgumentException("Stock cannot be null");
        if (stock < 0)
            throw new IllegalArgumentException("Stock cannot be negative");
    }

    public Integer getStock() {
        return stock;
    }

    public Stock add(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity to add must be positive");
        }
        return new Stock(this.stock + quantity);
    }

    public Stock subtract(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity to subtract must be positive");
        }
        return new Stock(this.stock - quantity);
    }

}