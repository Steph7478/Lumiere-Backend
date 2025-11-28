package com.lumiere.domain.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.lumiere.domain.entities.base.BaseEntity;
import com.lumiere.domain.enums.CurrencyEnum.CurrencyType;
import com.lumiere.domain.enums.StatusEnum.Status;
import com.lumiere.domain.vo.OrderItem;

public class Order extends BaseEntity {

    private final User user;
    private final Status status;
    private final UUID paymentId;
    private final String coupon;
    private final BigDecimal total;
    private final CurrencyType currency;
    private final List<OrderItem> items;

    @FunctionalInterface
    private interface ItemOperation {
        int apply(int currentQuantity, int modificationQuantity);
    }

    public Order(UUID id, User user, Status status, UUID paymentId, BigDecimal total, List<OrderItem> items,
            String coupon, CurrencyType currency) {
        super(id);
        this.user = Objects.requireNonNull(user, "User cannot be null");
        this.status = status != null ? status : Status.IN_PROGRESS;
        this.paymentId = paymentId;
        this.total = Objects.requireNonNull(total, "total cannot be null");
        this.items = Objects.requireNonNull(items, "Order items cannot be null");
        this.coupon = coupon;
        this.currency = Objects.requireNonNull(currency, "Currency cannot be null");
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

    public CurrencyType getCurrency() {
        return currency;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public Order useCoupon(String coupon) {
        return new Order(getId(), this.user, this.status, this.paymentId,
                this.total, this.items, coupon, this.currency);
    }

    public Order markAsPaid(UUID paymentId) {
        return new Order(getId(), this.user, Status.PAID, Objects.requireNonNull(paymentId, "paymentId cannot be null"),
                this.total, this.items, this.coupon, this.currency);
    }

    public Order addItem(UUID productId, int quantity, BigDecimal price) {
        if (productId == null || quantity <= 0 || price == null) {
            return this;
        }

        return this.updateItem(productId, quantity,
                (current, modification) -> current + modification,
                true, price);
    }

    public Order updateQuantity(UUID productId, int newQuantity) {
        if (productId == null || newQuantity < 0) {
            return this;
        }

        if (newQuantity == 0) {
            return this.removeItem(productId);
        }

        return this.updateItem(productId, newQuantity,
                (current, modification) -> modification,
                false, null);
    }

    public Order removeItem(UUID productId) {
        List<OrderItem> newItems = new ArrayList<>(this.items);

        Optional<OrderItem> existingItem = newItems.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        existingItem.ifPresent(newItems::remove);

        BigDecimal newTotal = calculateTotal(newItems);

        return new Order(getId(), this.user, this.status, this.paymentId,
                newTotal, newItems, this.coupon, this.currency);
    }

    private Order updateItem(UUID productId, int modificationQuantity, ItemOperation operation,
            boolean shouldAddifMissing, BigDecimal price) {
        List<OrderItem> newItems = new ArrayList<>(this.items);

        Optional<OrderItem> existingItem = newItems.stream().filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            OrderItem oldItem = existingItem.get();
            newItems.remove(oldItem);

            int newQuantity = operation.apply(oldItem.getQuantity(), modificationQuantity);

            if (newQuantity > 0) {
                newItems.add(oldItem.withQuantity(newQuantity));
            }

            BigDecimal calculatedTotal = calculateTotal(newItems);

            return new Order(getId(), this.user, this.status, this.paymentId, calculatedTotal, newItems, this.coupon,
                    this.currency);
        } else if (shouldAddifMissing && modificationQuantity > 0) {
            BigDecimal itemSubtotal = price.multiply(BigDecimal.valueOf(modificationQuantity));

            newItems.add(new OrderItem(productId, this.coupon, modificationQuantity, itemSubtotal));

            BigDecimal calculatedTotal = calculateTotal(newItems);

            return new Order(getId(), this.user, this.status, this.paymentId, calculatedTotal, newItems, this.coupon,
                    this.currency);
        }

        return this;
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