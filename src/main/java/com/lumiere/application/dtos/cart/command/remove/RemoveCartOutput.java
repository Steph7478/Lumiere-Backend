package com.lumiere.application.dtos.cart.command.remove;

import com.lumiere.domain.readmodels.CartReadModel;

import jakarta.validation.constraints.NotNull;

public record RemoveCartOutput(@NotNull CartReadModel cart) {

}
