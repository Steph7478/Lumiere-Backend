package com.lumiere.application.dtos.cart.command.add;

import java.util.List;
import java.util.UUID;

import com.lumiere.domain.vo.CartItem;

public record AddCartOuput(UUID id, List<CartItem> items, String coupon) {

}