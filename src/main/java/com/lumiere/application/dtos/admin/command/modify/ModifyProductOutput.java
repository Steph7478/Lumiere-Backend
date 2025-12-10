package com.lumiere.application.dtos.admin.command.modify;

import java.util.UUID;

import jakarta.validation.constraints.*;

public record ModifyProductOutput(@NotNull UUID id, @NotBlank String name, @NotBlank String description) {
}
