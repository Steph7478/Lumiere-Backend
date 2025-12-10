package com.lumiere.application.dtos.cart.command.add;

import com.lumiere.domain.readmodels.CartReadModel;

import jakarta.validation.constraints.*;

public record AddCartOuput(
        @NotNull CartReadModel cart) {
}