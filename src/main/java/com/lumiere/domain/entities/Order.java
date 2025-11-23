package com.lumiere.domain.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.lumiere.domain.entities.base.BaseEntity;
import com.lumiere.domain.enums.StatusEnum.Status;
import com.lumiere.domain.vo.OrderItem;

public class Order extends BaseEntity {

    private final User user;
    private final Status status;
    private final UUID paymentId;
    private final String coupon;
    private final BigDecimal total;
    private final List<OrderItem> items;

    public Order(UUID id, User user, Status status, UUID paymentId, BigDecimal total, List<OrderItem> items,
            String coupon) {
        super(id);
        this.user = Objects.requireNonNull(user, "User cannot be null");
        this.status = status != null ? status : Status.IN_PROGRESS;
        this.paymentId = paymentId;
        this.total = Objects.requireNonNull(total, "total cannot be null");
        this.items = Objects.requireNonNull(items, "Order items cannot be null");
        this.coupon = coupon;
    }

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

    public String getCoupon() {
        return coupon;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public Order useCoupon(String coupon) {
        return new Order(getId(), this.user, this.status, this.paymentId,
                this.total, this.items, coupon);
    }

    public Order removeItem(UUID productId) {
        List<OrderItem> newItems = new ArrayList<>(this.items);

        Optional<OrderItem> existingItem = newItems.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        existingItem.ifPresent(newItems::remove);

        BigDecimal newTotal = calculateTotal(newItems);

        return new Order(getId(), this.user, this.status, this.paymentId,
                newTotal, newItems, this.coupon);
    }

    public Order markAsPaid(UUID paymentId) {
        return new Order(getId(), this.user, Status.PAID, Objects.requireNonNull(paymentId, "paymentId cannot be null"),
                this.total, this.items, this.coupon);
    }

    private static BigDecimal calculateTotal(List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(OrderItem::calculateSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}