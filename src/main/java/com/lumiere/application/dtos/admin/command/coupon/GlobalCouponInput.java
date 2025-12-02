package com.lumiere.application.dtos.admin.command.coupon;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.enums.CouponEnum.Type;

public record GlobalCouponInput(Category category, Type type, BigDecimal discount, LocalDateTime expiredAt,
        String code) {

}
