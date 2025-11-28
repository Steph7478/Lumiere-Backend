package com.lumiere.application.dtos.order.command.create;

import java.util.UUID;

public record CreateOrderInput(UUID authId, CreateOrderRequestData requestData) {

}
