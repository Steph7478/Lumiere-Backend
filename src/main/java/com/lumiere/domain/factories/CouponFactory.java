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

    public static Coupon createUniqueCoupon(Category category, User user, Type type, BigDecimal discount) {

        Objects.requireNonNull(user, "User entity is required to create a new coupon.");

        String generatedCode = CouponCodeGenerator.generate();

        BigDecimal defaultDiscount = new BigDecimal("10.00");
        Type defaultType = Type.PERCENTAGE;
        boolean defaultIsUnique = true;

        return new Coupon(
                null,
                LocalDateTime.now().plusDays(7),
                category,
                discount != null ? discount : defaultDiscount,
                type != null ? type : defaultType,
                defaultIsUnique,
                user,
                generatedCode);
    }

    public static Coupon createGlobalCoupon(
            Category category,
            Type type,
            BigDecimal discount,
            LocalDateTime expiredAt,
            String code) {
        Objects.requireNonNull(code, "Global coupon code must be manually provided.");

        User user = null;
        BigDecimal defaultDiscount = new BigDecimal("10.00");
        Type defaultType = Type.PERCENTAGE;
        boolean defaultIsUnique = false;

        return new Coupon(
                null,
                expiredAt != null ? expiredAt : LocalDateTime.now().plusDays(7),
                category,
                discount != null ? discount : defaultDiscount,
                type != null ? type : defaultType,
                defaultIsUnique,
                user,
                code);
    }
}