package com.lumiere.application.dtos.payment.command.checkout;

public record CreateCheckoutSessionRequestData(String successUrl, String cancelUrl) {

}
