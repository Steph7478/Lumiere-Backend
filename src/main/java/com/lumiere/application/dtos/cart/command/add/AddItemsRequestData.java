package com.lumiere.application.dtos.cart.command.add;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.*;

public record AddItemsRequestData(List<InnerAddItemsRequestData> items) {
    public record InnerAddItemsRequestData(@NotNull UUID productId, @Min(1) @Max(100) int quantity) {
    }
}
