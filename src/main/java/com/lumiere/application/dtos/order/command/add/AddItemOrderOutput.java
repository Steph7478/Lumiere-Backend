package com.lumiere.application.dtos.order.command.add;

import com.lumiere.domain.readmodels.OrderReadModel;

import jakarta.validation.constraints.NotNull;

public record AddItemOrderOutput(@NotNull OrderReadModel order) {

}
