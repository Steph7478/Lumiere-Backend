package com.lumiere.domain.vo;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.vo.base.ValueObject;

public class OrderItem extends ValueObject {

    private final UUID productId;
    private final String name;
    private final int quantity;
    private final BigDecimal unitPrice;
    private final Category category;

    public OrderItem(UUID productId, String name, int quantity, BigDecimal unitPrice, Category category) {
        this.productId = Objects.requireNonNull(productId, "Product ID cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.unitPrice = Objects.requireNonNull(unitPrice, "Unit Price cannot be null");
        this.quantity = quantity;
        this.category = category;
        validate();
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

    public Category getCategory() {
        return category;
    }

    public OrderItem withQuantity(int newQuantity) {
        return new OrderItem(this.productId, this.name, newQuantity, this.unitPrice, this.category);
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal calculateSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public UUID getId() {
        return UUID.randomUUID();
    }
}