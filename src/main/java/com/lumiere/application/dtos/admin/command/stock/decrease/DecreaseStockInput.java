package com.lumiere.application.dtos.admin.command.stock.decrease;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record DecreaseStockInput(@NotNull UUID id, @NotNull DecreaseStockRequestData requestData) {

}
