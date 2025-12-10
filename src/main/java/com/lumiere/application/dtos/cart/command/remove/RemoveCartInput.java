package com.lumiere.application.dtos.cart.command.remove;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record RemoveCartInput(@NotNull UUID authId, @NotNull RemoveMultipleItemsRequestData requestData) {

}
