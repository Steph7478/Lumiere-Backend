package com.lumiere.application.dtos.admin.command.modify;

import java.util.UUID;

public record ModifyProductInput(UUID id, ModifyProductRequestData requestData) {
}
