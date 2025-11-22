package com.lumiere.application.dtos.cart.command.remove;

import java.util.List;
import java.util.UUID;

public record RemoveMultipleItemsRequestData(List<UUID> productIds) {

}
