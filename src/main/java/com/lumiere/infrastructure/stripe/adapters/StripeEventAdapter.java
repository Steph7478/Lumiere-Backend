package com.lumiere.infrastructure.stripe.adapters;

import com.lumiere.application.webhooks.WebhookEvent;
import com.stripe.model.Event;

public interface StripeEventAdapter {
    boolean supports(String eventType);

    WebhookEvent convert(Event event);
}
