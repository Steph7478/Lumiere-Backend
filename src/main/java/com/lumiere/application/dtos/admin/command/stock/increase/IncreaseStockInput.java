package com.lumiere.application.dtos.admin.command.stock.increase;

import java.util.UUID;

public record IncreaseStockInput(UUID id, int quantity) {

}
