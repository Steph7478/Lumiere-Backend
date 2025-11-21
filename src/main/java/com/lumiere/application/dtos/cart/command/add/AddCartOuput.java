package com.lumiere.application.dtos.cart.command.add;

import com.lumiere.domain.readmodels.CartItemReadModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record AddCartOuput(
                UUID id,
                List<CartItemReadModel> items,
                LocalDateTime createdAt,
                LocalDateTime updatedAt,
                String coupon) {
}