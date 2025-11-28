package com.lumiere.application.dtos.order.command.coupon;

import java.util.UUID;

public record AddCouponInput(UUID userId, AddCouponRequestData coupon) {

}
