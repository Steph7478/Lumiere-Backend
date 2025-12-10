package com.lumiere.application.dtos.admin.command.add;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record AddProductInput(@NotNull List<AddProductRequestData> items) {
}
