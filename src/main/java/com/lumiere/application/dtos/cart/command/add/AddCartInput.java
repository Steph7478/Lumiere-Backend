package com.lumiere.application.dtos.cart.command.add;

import java.util.UUID;

public record AddCartInput(UUID authId, AddMultipleItemsRequestData requestData) {
}
