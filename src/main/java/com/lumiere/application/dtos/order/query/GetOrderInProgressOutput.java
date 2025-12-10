package com.lumiere.application.dtos.order.query;

import com.lumiere.domain.readmodels.OrderReadModel;

import jakarta.validation.constraints.NotNull;

public record GetOrderInProgressOutput(@NotNull OrderReadModel order) {

}
