package com.lumiere.application.dtos.order.command.add;

import java.util.UUID;

public record AddItemOrderRequestData(UUID productId, int quantity) {

}
