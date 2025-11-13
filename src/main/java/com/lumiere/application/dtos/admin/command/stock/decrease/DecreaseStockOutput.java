package com.lumiere.application.dtos.admin.command.stock.decrease;

import java.util.UUID;

import com.lumiere.domain.vo.Stock;

public record DecreaseStockOutput(UUID id, Stock stock) {

}
