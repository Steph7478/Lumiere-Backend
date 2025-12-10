package com.lumiere.application.dtos.order.query;

import java.util.List;

import com.lumiere.domain.readmodels.OrderReadModel;

import jakarta.validation.constraints.NotNull;

public record GetOrdersOutput(@NotNull List<OrderReadModel> order) {

}
