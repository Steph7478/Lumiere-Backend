package com.lumiere.application.dtos.admin.command.remove;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record RemoveProductInput(@NotNull List<UUID> productsId) {

}
