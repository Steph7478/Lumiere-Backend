package com.lumiere.application.dtos.coupon.query;

import java.util.List;

import com.lumiere.domain.entities.Coupon;

public record AvalibleCouponsOutput(List<Coupon> coupons) {

}
