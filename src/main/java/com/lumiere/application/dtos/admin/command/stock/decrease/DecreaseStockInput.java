package com.lumiere.application.dtos.admin.command.stock.decrease;

import java.util.UUID;

public record DecreaseStockInput(UUID id, int quantity) {

}
