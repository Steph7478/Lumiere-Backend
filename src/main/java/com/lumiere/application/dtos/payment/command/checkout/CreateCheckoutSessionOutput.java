package com.lumiere.application.dtos.payment.command.checkout;

import jakarta.validation.constraints.NotBlank;

public record CreateCheckoutSessionOutput(
                @NotBlank String checkoutUrl) {
}