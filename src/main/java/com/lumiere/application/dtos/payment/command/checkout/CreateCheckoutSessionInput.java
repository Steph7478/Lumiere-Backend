package com.lumiere.application.dtos.payment.command.checkout;

import java.util.UUID;

public record CreateCheckoutSessionInput(UUID userId, CreateCheckoutSessionRequestData data) {

}
