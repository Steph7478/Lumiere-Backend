package com.lumiere.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Order {

    private final UUID id;
    private final LocalDateTime createdAt;
    private final Cart cart;
    private final Status status;
    private final UUID paymentId;
    private final BigDecimal total;
    private final LocalDateTime orderDate;

    private Order(UUID id, Cart cart, Status status, UUID paymentId, BigDecimal total, LocalDateTime orderDate) {
        this.id = id != null ? id : UUID.randomUUID();
        this.cart = Objects.requireNonNull(cart, "cart cannot be null");
        this.createdAt = LocalDateTime.now();
        this.status = status != null ? status : Status.IN_PROGRESS;
        this.paymentId = paymentId;
        this.total = Objects.requireNonNull(total, "total cannot be null");
        this.orderDate = orderDate;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Cart getCart() {
        return cart;
    }

    public Status getStatus() {
        return status;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Order markAsPaid(UUID paymentId) {
        return new Order(this.id, this.cart, Status.PAID, Objects.requireNonNull(paymentId, "paymentId cannot be null"),
                this.total, LocalDateTime.now());
    }

    public Order recalculateTotal(BigDecimal newTotal) {
        return new Order(this.id, this.cart, this.status, this.paymentId,
                Objects.requireNonNull(newTotal, "total cannot be null"), this.orderDate);
    }

    // Enums
    public enum Status {
        IN_PROGRESS,
        PAID
    }

    // factory
    public static Order createOrder(Cart cart, BigDecimal total) {
        return new Order(null, cart, Status.IN_PROGRESS, null, total, null);
    }
}
