package com.lumiere.application.dtos.order.command.create;

import java.util.List;

import com.lumiere.domain.enums.CurrencyEnum.CurrencyType;
import com.lumiere.domain.vo.CartItem;

public record CreateOrderRequestData(List<CartItem> items, CurrencyType currency) {

}
