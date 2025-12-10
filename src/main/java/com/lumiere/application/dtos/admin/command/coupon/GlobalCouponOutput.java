package com.lumiere.application.dtos.admin.command.coupon;

import com.lumiere.domain.entities.Coupon;

import jakarta.validation.constraints.NotNull;

public record GlobalCouponOutput(@NotNull Coupon coupon) {

}
