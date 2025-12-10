package com.lumiere.application.dtos.admin.command.coupon;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.enums.CouponEnum.Type;

import jakarta.validation.constraints.*;

public record GlobalCouponInput(@NotNull Category category, @NotNull Type type, @NotNull BigDecimal discount,
                @NotNull LocalDateTime expiredAt,
                @NotBlank String code) {

}
