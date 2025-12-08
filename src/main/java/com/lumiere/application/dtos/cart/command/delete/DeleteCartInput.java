package com.lumiere.application.dtos.cart.command.delete;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record DeleteCartInput(@NotNull UUID id) {

}
