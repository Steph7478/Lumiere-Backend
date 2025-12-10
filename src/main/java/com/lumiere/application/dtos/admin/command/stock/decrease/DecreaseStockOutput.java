package com.lumiere.application.dtos.admin.command.stock.decrease;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record DecreaseStockOutput(@NotNull UUID id, @NotNull int stock) {

}
