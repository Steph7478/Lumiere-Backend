package com.lumiere.application.dtos.coupon.query;

import java.util.List;

import com.lumiere.domain.entities.Coupon;

import jakarta.validation.constraints.NotNull;

public record AvalibleCouponsOutput(@NotNull List<Coupon> coupons) {

}
