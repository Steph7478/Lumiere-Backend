package com.lumiere.application.dtos.payment.command.checkout;

import jakarta.validation.constraints.NotBlank;

public record CreateCheckoutSessionRequestData(@NotBlank String successUrl, @NotBlank String cancelUrl) {

}
