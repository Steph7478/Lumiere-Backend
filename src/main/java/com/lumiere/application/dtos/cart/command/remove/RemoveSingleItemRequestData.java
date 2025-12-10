package com.lumiere.application.dtos.cart.command.remove;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record RemoveSingleItemRequestData(@NotNull UUID productId, @NotNull int quantity) {

}
