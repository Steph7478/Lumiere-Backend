package com.lumiere.application.dtos.order.command.add;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record AddItemOrderInput(
        @NotNull UUID userId, @NotNull AddItemOrderRequestData items) {
}
