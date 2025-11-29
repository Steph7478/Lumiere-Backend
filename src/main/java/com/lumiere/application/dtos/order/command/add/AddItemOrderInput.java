package com.lumiere.application.dtos.order.command.add;

import java.util.UUID;

public record AddItemOrderInput(
                UUID userId, AddItemOrderRequestData items) {
}
