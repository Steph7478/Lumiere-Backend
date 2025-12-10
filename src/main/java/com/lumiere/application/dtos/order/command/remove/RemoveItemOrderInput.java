package com.lumiere.application.dtos.order.command.remove;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record RemoveItemOrderInput(@NotNull UUID userId, @NotNull RemoveItemRequestData items) {

}
