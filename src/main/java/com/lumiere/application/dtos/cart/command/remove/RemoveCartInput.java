package com.lumiere.application.dtos.cart.command.remove;

import java.util.UUID;

public record RemoveCartInput(UUID authId, RemoveMultipleItemsRequestData requestData) {

}
