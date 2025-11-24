package com.lumiere.application.dtos.order.command.create;

import java.util.UUID;

public record CreateOrderRequestData(UUID productId, int quantity) {

}
