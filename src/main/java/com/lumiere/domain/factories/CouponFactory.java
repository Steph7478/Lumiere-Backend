package com.lumiere.domain.factories;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Objects;

import com.lumiere.domain.entities.Coupon;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.enums.CouponEnum.Type;
import com.lumiere.domain.utils.CouponCodeGenerator;

public class CouponFactory {

    public static Coupon from(
            UUID id,
            LocalDateTime expiredAt,
            Category category,
            BigDecimal discount,
            Type type,
            boolean isUnique,
            User user,
            String code) {

        Objects.requireNonNull(user, "User entity is required for coupon reconstruction.");

        return new Coupon(
                id,
                expiredAt,
                category,
                discount,
                type,
                isUnique,
                user,
                code);
    }

    public static Coupon createPromotionalCoupon(Category category, User user) {

        Objects.requireNonNull(user, "User entity is required to create a new coupon.");

        String generatedCode = CouponCodeGenerator.generate();

        BigDecimal defaultDiscount = new BigDecimal("10.00");
        Type defaultType = Type.PERCENTAGE;
        boolean defaultIsUnique = false;

        return new Coupon(
                null,
                LocalDateTime.now().plusDays(7),
                category,
                defaultDiscount,
                defaultType,
                defaultIsUnique,
                user,
                generatedCode);
    }
}