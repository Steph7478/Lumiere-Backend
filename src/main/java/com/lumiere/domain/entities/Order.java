package com.lumiere.domain.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.math.RoundingMode;
import com.lumiere.domain.entities.base.BaseEntity;
import com.lumiere.domain.enums.CurrencyEnum.CurrencyType;
import com.lumiere.domain.enums.StatusEnum.Status;
import com.lumiere.domain.enums.CouponEnum.Type;
import com.lumiere.domain.vo.OrderItem;

public class Order extends BaseEntity {

    private final User user;
    private final Status status;
    private final String paymentId;
    private final Coupon coupon;
    private final BigDecimal total;
    private final CurrencyType currency;
    private final List<OrderItem> items;

    @FunctionalInterface
    private interface ItemOperation {
        int apply(int currentQuantity, int modificationQuantity);
    }

    public Order(UUID id, User user, Status status, String paymentId, BigDecimal total, List<OrderItem> items,
            Coupon coupon, CurrencyType currency) {
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

    public String getPaymentId() {
        return paymentId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public Order useCoupon(Coupon coupon) {
        if (coupon == null || !coupon.isValid()) {
            BigDecimal currentSubtotal = calculateSubtotal(this.items);
            return new Order(getId(), this.user, this.status, this.paymentId,
                    currentSubtotal, this.items, null, this.currency);
        }

        BigDecimal currentSubtotal = calculateSubtotal(this.items);
        BigDecimal discountValue;

        if (coupon.getType() == Type.PERCENTAGE) {
            BigDecimal percentage = coupon.getDiscount().divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);

            discountValue = currentSubtotal.multiply(percentage).setScale(2, RoundingMode.HALF_UP);
        } else {
            discountValue = coupon.getDiscount().setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal newTotal = currentSubtotal.subtract(discountValue).setScale(2, RoundingMode.HALF_UP);
        if (newTotal.compareTo(BigDecimal.ZERO) < 0) {
            newTotal = BigDecimal.ZERO;
        }

        return new Order(getId(), this.user, this.status, this.paymentId,
                newTotal, this.items, coupon, this.currency);
    }

    public Order markAsPaid(String paymentId) {
        return new Order(getId(), this.user, Status.PAID, Objects.requireNonNull(paymentId, "paymentId cannot be null"),
                this.total, this.items, this.coupon, this.currency);
    }

    public Order addItem(UUID productId, String productName, int quantity, BigDecimal price) {
        if (productId == null || quantity <= 0 || price == null || productName == null)
            return this;

        Order updatedOrder = this.updateItem(productId, productName, quantity,
                (current, modification) -> current + modification,
                true, price);

        return updatedOrder.applyCurrentCoupon();
    }

    public Order updateQuantity(UUID productId, int newQuantity) {
        if (productId == null || newQuantity < 0)
            return this;

        if (newQuantity == 0)
            return this.removeItem(productId);

        Order updatedOrder = this.updateItem(productId, null, newQuantity,
                (current, modification) -> modification,
                false, null);

        return updatedOrder.applyCurrentCoupon();
    }

    public Order removeItem(UUID productId) {
        List<OrderItem> newItems = new ArrayList<>(this.items);

        Optional<OrderItem> existingItem = newItems.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        existingItem.ifPresent(newItems::remove);

        BigDecimal calculatedSubtotal = calculateSubtotal(newItems);

        Order orderWithoutCouponApplied = new Order(getId(), this.user, this.status, this.paymentId,
                calculatedSubtotal, newItems, this.coupon, this.currency);

        return orderWithoutCouponApplied.applyCurrentCoupon();
    }

    private Order updateItem(UUID productId, String productName, int modificationQuantity, ItemOperation operation,
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

            BigDecimal calculatedSubtotal = calculateSubtotal(newItems);

            return new Order(getId(), this.user, this.status, this.paymentId, calculatedSubtotal, newItems, this.coupon,
                    this.currency);

        } else if (shouldAddifMissing && modificationQuantity > 0) {
            newItems.add(new OrderItem(productId, productName, modificationQuantity, price, null));

            BigDecimal calculatedSubtotal = calculateSubtotal(newItems);

            return new Order(getId(), this.user, this.status, this.paymentId, calculatedSubtotal, newItems, this.coupon,
                    this.currency);
        }

        return this;
    }

    private Order applyCurrentCoupon() {
        return this.useCoupon(this.coupon);
    }

    private static BigDecimal calculateSubtotal(List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(OrderItem::calculateSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }
}