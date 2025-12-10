package com.lumiere.application.dtos.cart.command.remove;

import java.util.List;

import com.lumiere.domain.vo.CartItem;

import jakarta.validation.constraints.NotNull;

public record RemoveMultipleItemsRequestData(@NotNull List<CartItem> items) {

}
