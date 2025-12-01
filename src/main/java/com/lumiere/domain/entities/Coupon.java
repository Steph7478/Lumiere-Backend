package com.lumiere.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.lumiere.domain.entities.base.BaseEntity;
import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.enums.CouponEnum.Type;

public class Coupon extends BaseEntity {

    private final LocalDateTime expiredAt;
    private final Category category;
    private final BigDecimal discount;
    private final Type type;
    private final boolean isUnique;
    private final User user;
    // TODO: Value Object of Code
    private final String code;

    public Coupon(UUID id, LocalDateTime expiredAt, Category category,
            BigDecimal discount, Type type, boolean isUnique, User user, String code) {

        super(id);
        this.expiredAt = Objects.requireNonNull(expiredAt, "expiredAt cannot be null");
        if (expiredAt.isBefore(this.getCreatedAt()))
            throw new IllegalArgumentException("expiredAt cannot be before the creation date (createdAt)");

        this.discount = Objects.requireNonNull(discount, "discount cannot be null");
        this.type = Objects.requireNonNull(type, "type cannot be null");

        if (discount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Discount must be positive.");

        if (type == Type.PERCENTAGE && discount.compareTo(new BigDecimal("100")) > 0)
            throw new IllegalArgumentException("Percentage discount cannot exceed 100.");

        this.user = user;
        if (user == null && isUnique)
            throw new IllegalArgumentException("A global coupon (user is null) cannot be unique.");

        this.category = Objects.requireNonNull(category, "category cannot be null");

        this.code = Objects.requireNonNull(code, "code cannot be null");
        this.isUnique = isUnique;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public Category getCategory() {
        return category;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public Type getType() {
        return type;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public User getUser() {
        return user;
    }

    public String getCode() {
        return code;
    }

    public boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        return !now.isBefore(this.getCreatedAt()) && !now.isAfter(expiredAt);
    }
}