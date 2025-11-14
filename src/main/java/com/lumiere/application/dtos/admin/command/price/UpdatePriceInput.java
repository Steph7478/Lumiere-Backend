package com.lumiere.application.dtos.admin.command.price;

import java.util.UUID;

public record UpdatePriceInput(UUID id, UpdatePriceRequestData requestData) {

}
