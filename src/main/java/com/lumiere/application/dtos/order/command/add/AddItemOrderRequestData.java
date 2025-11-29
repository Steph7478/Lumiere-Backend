package com.lumiere.application.dtos.order.command.add;

import java.util.List;
import java.util.UUID;

public record AddItemOrderRequestData(List<OrderItemData> items) {
    public record OrderItemData(UUID productId, int quantity) {
    }
}