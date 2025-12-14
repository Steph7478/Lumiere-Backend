package com.lumiere.infrastructure.stripe.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lumiere.application.webhooks.WebhookEvent;
import com.lumiere.infrastructure.stripe.dto.StripeCheckoutSessionPayload;
import com.stripe.model.Event;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CheckoutSessionCompletedAdapter implements StripeEventAdapter {

    private final ObjectMapper mapper;

    public CheckoutSessionCompletedAdapter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean supports(String eventType) {
        return "checkout.session.completed".equals(eventType);
    }

    @Override
    public WebhookEvent convert(Event event) {

        String rawJson = event.getDataObjectDeserializer().getRawJson();

        if (rawJson == null) {
            throw new IllegalArgumentException(
                    "Stripe webhook has no data.object");
        }

        try {
            StripeCheckoutSessionPayload payload = mapper.readValue(
                    rawJson,
                    StripeCheckoutSessionPayload.class);

            return new WebhookEvent(
                    "stripe",
                    "payment.succeeded",
                    Map.of(
                            "orderId", payload.orderId(),
                            "userId", payload.userId(),
                            "paymentId", payload.paymentId(),
                            "createdAt", payload.createdAt(),
                            "paymentMethod", payload.paymentMethod()));

        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Failed to parse Stripe Checkout Session", e);
        }
    }
}
