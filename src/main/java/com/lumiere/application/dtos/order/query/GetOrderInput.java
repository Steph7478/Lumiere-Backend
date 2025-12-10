package com.lumiere.application.dtos.order.query;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record GetOrderInput(@NotNull UUID userId) {

}
