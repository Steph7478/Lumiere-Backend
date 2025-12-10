package com.lumiere.application.dtos.order.command.coupon;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record AddCouponInput(@NotNull UUID userId, @NotNull AddCouponRequestData coupon) {

}
