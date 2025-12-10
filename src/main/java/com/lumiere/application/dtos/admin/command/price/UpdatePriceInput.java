package com.lumiere.application.dtos.admin.command.price;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record UpdatePriceInput(@NotNull UUID id, @NotNull UpdatePriceRequestData requestData) {

}
