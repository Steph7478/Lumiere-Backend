package com.lumiere.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.lumiere.domain.entities.base.BaseEntity;
import com.lumiere.domain.utils.CouponCodeGenerator;

public class Coupon extends BaseEntity {

    private final LocalDateTime couponDate;
    private final LocalDateTime expiredAt;
    private final Category category;
    private final UUID userId;
    private final String code;

    private Coupon(UUID id, LocalDateTime couponDate, LocalDateTime expiredAt, Category category, UUID userId,
            String code) {
        super(id);
        this.couponDate = Objects.requireNonNull(couponDate, "couponDate cannot be null");
        this.expiredAt = Objects.requireNonNull(expiredAt, "expiredAt cannot be null");
        if (expiredAt.isBefore(couponDate)) {
            throw new IllegalArgumentException("expiredAt cannot be before couponDate");
        }
        this.category = Objects.requireNonNull(category, "category cannot be null");
        this.userId = Objects.requireNonNull(userId, "userId cannot be null");
        this.code = Objects.requireNonNull(code, "code cannot be null");
    }

    // Getters
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

    public String getCode() {
        return code;
    }

    // Enums
    public enum Category {
        COUPON_1, COUPON_2
    }

    public enum Type {
        PERCENTAGE, FIXED
    }

    public boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        return !now.isBefore(couponDate) && !now.isAfter(expiredAt);
    }

    public static Coupon createCoupon(UUID userId, Category category) {
        String generatedCode = CouponCodeGenerator.generate();
        return new Coupon(
                null,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                category,
                userId,
                generatedCode);
    }
}
