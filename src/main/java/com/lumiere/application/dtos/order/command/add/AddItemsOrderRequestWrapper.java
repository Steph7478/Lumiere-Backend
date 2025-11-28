package com.lumiere.application.dtos.order.command.add;

import java.util.List;
import jakarta.validation.constraints.NotEmpty;

public record AddItemsOrderRequestWrapper(
        @NotEmpty List<AddItemOrderRequestData> items) {
}