package com.lumiere.application.dtos.admin.command.stock.increase;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record IncreaseStockOutput(@NotNull UUID id, @NotNull int stock) {

}
