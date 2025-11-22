package com.lumiere.application.dtos.cart.command.add;

import java.util.UUID;

public record AddSingleItemRequestData(UUID productId, int quantity) {

}
