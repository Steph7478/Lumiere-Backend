package com.lumiere.application.dtos.admin.command.modify;

import java.util.UUID;

public record ModifyProductOutput(UUID id, String name, String description) {
}
