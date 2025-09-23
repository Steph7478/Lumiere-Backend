package com.lumiere.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Order {

    private final UUID id;
    private LocalDateTime orderDate;
    private Status status;
    private Cart cart;
    private UUID paymentId;
    private BigDecimal total;

    public Order(UUID id, LocalDateTime orderDate, Status status, Cart cart, UUID paymentId, BigDecimal total) {
        this.id = id != null ? id : UUID.randomUUID();
        this.orderDate = orderDate;
        this.status = status;
        this.cart = cart;
        this.paymentId = paymentId;
        this.total = total;
    }

    // getters
    public UUID getId() {
        return id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Status getStatus() {
        return status;
    }

    public Cart getCart() {
        return cart;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    // setters
    public void setOrderDate(LocalDateTime orderDate) {
        if (this.status != Status.PAID) {
            this.orderDate = null;
        }

        this.orderDate = LocalDateTime.now();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    // enums
    public enum Status {
        IN_PROGRESS,
        PAID
    }
}
