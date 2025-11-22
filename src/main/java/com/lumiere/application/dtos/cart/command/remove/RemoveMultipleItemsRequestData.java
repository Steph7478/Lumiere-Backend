package com.lumiere.application.dtos.cart.command.remove;

import java.util.List;

import com.lumiere.domain.vo.CartItem;

public record RemoveMultipleItemsRequestData(List<CartItem> items) {

}
