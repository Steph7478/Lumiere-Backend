package com.lumiere.application.dtos.order.command.cancel;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CancelOrderInput(@NotNull UUID userId) {

}
