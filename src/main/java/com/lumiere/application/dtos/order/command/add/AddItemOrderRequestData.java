package com.lumiere.application.dtos.order.command.add;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record AddItemOrderRequestData(@NotNull List<OrderItemData> items) {
    public record OrderItemData(@NotNull UUID productId, @NotNull int quantity) {
    }
}