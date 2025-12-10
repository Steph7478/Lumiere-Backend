package com.lumiere.application.dtos.order.command.remove;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record RemoveItemRequestData(@NotNull List<UUID> productsId) {

}
