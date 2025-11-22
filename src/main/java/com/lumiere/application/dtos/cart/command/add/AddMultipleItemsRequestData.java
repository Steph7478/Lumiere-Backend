package com.lumiere.application.dtos.cart.command.add;

import java.util.List;

import com.lumiere.domain.vo.CartItem;

public record AddMultipleItemsRequestData(List<CartItem> items) {
}
