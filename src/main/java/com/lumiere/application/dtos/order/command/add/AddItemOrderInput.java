package com.lumiere.application.dtos.order.command.add;

import java.util.List;
import java.util.UUID;

public record AddItemOrderInput(
        UUID userId, List<AddItemOrderRequestData> items) {

}
