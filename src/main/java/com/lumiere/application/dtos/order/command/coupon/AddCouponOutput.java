package com.lumiere.application.dtos.order.command.coupon;

import com.lumiere.domain.readmodels.OrderReadModel;

import jakarta.validation.constraints.NotNull;

public record AddCouponOutput(@NotNull OrderReadModel order) {

}
