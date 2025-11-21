package com.lumiere.application.dtos.cart.command.add;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.lumiere.domain.vo.CartItem;

public record AddCartRequestData(UUID authId, List<CartItem> items, Optional<String> coupon) {
}
