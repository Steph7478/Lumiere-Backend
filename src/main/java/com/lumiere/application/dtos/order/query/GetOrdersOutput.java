package com.lumiere.application.dtos.order.query;

import java.util.List;

import com.lumiere.domain.readmodels.OrderReadModel;

public record GetOrdersOutput(List<OrderReadModel> order) {

}
