package com.lumiere.infrastructure.stripe.dispatcher;

import com.lumiere.application.webhooks.WebhookEvent;
import com.lumiere.infrastructure.stripe.adapters.StripeEventAdapter;
import com.stripe.model.Event;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StripeWebhookEventDispatcher {

    private final List<StripeEventAdapter> adapters;

    public StripeWebhookEventDispatcher(List<StripeEventAdapter> adapters) {
        this.adapters = adapters;
    }

    public WebhookEvent dispatch(Event event) {
        return adapters.stream()
                .filter(a -> a.supports(event.getType()))
                .findFirst()
                .map(a -> a.convert(event))
                .orElse(null);
    }
}
