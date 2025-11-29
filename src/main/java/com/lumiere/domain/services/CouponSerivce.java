package com.lumiere.domain.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.lumiere.domain.entities.Coupon;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.enums.CouponEnum.Type;
import com.lumiere.domain.factories.CouponFactory;

public abstract class CouponSerivce {

    public static Coupon globalCoupon(Category category, Type type, BigDecimal discount, LocalDateTime expiredAt,
            String code) {
        return CouponFactory.createGlobalCoupon(category, type, discount, expiredAt, code);
    }

    public static Coupon uniqueCoupon(Category category, User user, Type type, BigDecimal discount) {
        return CouponFactory.createUniqueCoupon(category, user, type, discount);
    }
}
