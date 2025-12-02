package com.lumiere.application.dtos.cart.command.add;

import java.util.List;
import java.util.UUID;

public record AddItemsRequestData(List<InnerAddItemsRequestData> items) {
    public record InnerAddItemsRequestData(UUID productId, int quantity) {
    }
}
