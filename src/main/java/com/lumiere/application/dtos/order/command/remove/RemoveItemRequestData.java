package com.lumiere.application.dtos.order.command.remove;

import java.util.List;
import java.util.UUID;

public record RemoveItemRequestData(List<UUID> productsId) {

}
