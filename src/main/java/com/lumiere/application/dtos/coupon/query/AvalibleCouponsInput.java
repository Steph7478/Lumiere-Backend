package com.lumiere.application.dtos.coupon.query;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record AvalibleCouponsInput(@NotNull UUID userId) {

}
