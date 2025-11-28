package com.lumiere.application.dtos.order.command.remove;

import java.util.UUID;

public record RemoveItemOrderInput(UUID userId, RemoveItemRequestData items) {

}
