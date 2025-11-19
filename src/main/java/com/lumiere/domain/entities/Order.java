package com.lumiere.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.lumiere.domain.entities.base.BaseEntity;
import com.lumiere.domain.enums.StatusEnum.Status;
import com.lumiere.domain.vo.OrderItem;

public class Order extends BaseEntity {

    private final User user;
    private final Status status;
    private final UUID paymentId;
    private final BigDecimal total;
    private final LocalDateTime orderDate;
    private final List<OrderItem> items;

    private Order(UUID id, User user, Status status, UUID paymentId, BigDecimal total, LocalDateTime orderDate,
            List<OrderItem> items) {
        super(id);
        this.user = Objects.requireNonNull(user, "User cannot be null");
        this.status = status != null ? status : Status.IN_PROGRESS;
        this.paymentId = paymentId;
        this.total = Objects.requireNonNull(total, "total cannot be null");
        this.orderDate = orderDate;
        this.items = Objects.requireNonNull(items, "Order items cannot be null");
    }

    // Getters

    public User getUser() {
        return user;
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

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Order markAsPaid(UUID paymentId) {
        return new Order(getId(), this.user, Status.PAID, Objects.requireNonNull(paymentId, "paymentId cannot be null"),
                this.total, LocalDateTime.now(), this.items);
    }

    public Order recalculateTotal(BigDecimal newTotal) {
        return new Order(getId(), this.user, this.status, this.paymentId,
                Objects.requireNonNull(newTotal, "total cannot be null"), this.orderDate, this.items);
    }

    public static Order createOrder(Cart cart, BigDecimal total) {
        throw new UnsupportedOperationException(
                "Order factory method requires User object and OrderItems, not just Cart.");
    }
}