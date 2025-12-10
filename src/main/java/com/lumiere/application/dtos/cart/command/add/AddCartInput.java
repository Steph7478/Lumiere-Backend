package com.lumiere.application.dtos.cart.command.add;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record AddCartInput(@NotNull UUID authId, @NotNull AddItemsRequestData requestData) {
}
