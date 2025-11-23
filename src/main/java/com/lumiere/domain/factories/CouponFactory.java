package com.lumiere.domain.factories;

import java.time.LocalDateTime;
import java.util.UUID;

import com.lumiere.domain.entities.Coupon;
import com.lumiere.domain.entities.Coupon.Category;
import com.lumiere.domain.utils.CouponCodeGenerator;

public class CouponFactory {

    public static Coupon from(
            UUID id,
            LocalDateTime couponDate,
            LocalDateTime expiredAt,
            Category category,
            UUID userId,
            String code) {

        return new Coupon(id, couponDate, expiredAt, category, userId, code);
    }

    public static Coupon create(UUID userId, Category category) {
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