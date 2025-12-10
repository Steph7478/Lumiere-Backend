package com.lumiere.application.dtos.admin.command.modify;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record ModifyProductInput(@NotNull UUID id, @NotNull ModifyProductRequestData requestData) {
}
