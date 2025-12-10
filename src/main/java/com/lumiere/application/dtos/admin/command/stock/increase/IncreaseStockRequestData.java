package com.lumiere.application.dtos.admin.command.stock.increase;

import jakarta.validation.constraints.NotNull;

public record IncreaseStockRequestData(@NotNull int quantity) {

}
