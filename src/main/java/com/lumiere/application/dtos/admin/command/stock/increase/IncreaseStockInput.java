package com.lumiere.application.dtos.admin.command.stock.increase;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record IncreaseStockInput(@NotNull UUID id, @NotNull IncreaseStockRequestData requestData) {

}
