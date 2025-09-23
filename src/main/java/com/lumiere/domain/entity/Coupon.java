package com.lumiere.domain.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Coupon {

    private final UUID id;
    private final LocalDateTime couponDate;
    private final LocalDateTime expiredAt;
    private final Category category;
    private final UUID userId;

    private Coupon(UUID id, LocalDateTime couponDate, LocalDateTime expiredAt, Category category, UUID userId) {
        this.id = id != null ? id : UUID.randomUUID();
        this.couponDate = Objects.requireNonNull(couponDate, "couponDate cannot be null");
        this.expiredAt = Objects.requireNonNull(expiredAt, "expiredAt cannot be null");
        if (expiredAt.isBefore(couponDate)) {
            throw new IllegalArgumentException("expiredAt cannot be before couponDate");
        }
        this.category = Objects.requireNonNull(category, "category cannot be null");
        this.userId = Objects.requireNonNull(userId, "userId cannot be null");
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public LocalDateTime getCouponDate() {
        return couponDate;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public Category getCategory() {
        return category;
    }

    public UUID getUserId() {
        return userId;
    }

    // Enums
    public enum Category {
        COUPON_1, COUPON_2
    }

    public enum Type {
        PERCENTAGE,
        FIXED
    }

    public boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        return !now.isBefore(couponDate) && !now.isAfter(expiredAt);
    }

    // factory
    public static Coupon createCoupon(UUID userId, Category category) {
        return new Coupon(
                null,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                category,
                userId);
    }

}
