package com.lumiere.domain.vo;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public class OrderItem extends com.lumiere.domain.vo.base.ValueObject {

    private final UUID productId;
    private final String name;
    private final int quantity;
    private final BigDecimal unitPrice;

    public OrderItem(UUID productId, String name, int quantity, BigDecimal unitPrice) {
        this.productId = Objects.requireNonNull(productId, "Product ID cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.unitPrice = Objects.requireNonNull(unitPrice, "Unit Price cannot be null");

        validate();

        this.quantity = quantity;
    }

    @Override
    protected Stream<Object> getAtomicValues() {
        return Stream.of(productId, name, quantity, unitPrice);
    }

    @Override
    protected void validate() {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        if (unitPrice != null && unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Unit Price must be positive.");
        }
    }

    public UUID getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal calculateSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}