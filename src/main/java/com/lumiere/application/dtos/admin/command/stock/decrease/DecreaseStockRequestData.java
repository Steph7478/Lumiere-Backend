package com.lumiere.application.dtos.admin.command.stock.decrease;

import jakarta.validation.constraints.NotNull;

public record DecreaseStockRequestData(@NotNull int quantity) {

}
