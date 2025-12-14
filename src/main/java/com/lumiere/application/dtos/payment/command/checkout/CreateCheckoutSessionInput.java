package com.lumiere.application.dtos.payment.command.checkout;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CreateCheckoutSessionInput(@NotNull UUID userId, @NotNull CreateCheckoutSessionRequestData data) {

}
