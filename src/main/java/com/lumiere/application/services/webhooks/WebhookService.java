package com.lumiere.application.services.webhooks;

import com.lumiere.application.ports.WebhookPort;
import com.lumiere.application.webhooks.WebhookEvent;
import com.lumiere.application.webhooks.WebhookHandler;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class WebhookService implements WebhookPort {

    private final List<WebhookHandler> handlers;

    public WebhookService(List<WebhookHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void handle(WebhookEvent event) {
        handlers.stream()
                .filter(h -> h.supports(event.type()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "No handler for event type: " + event.type()))
                .handle(event);
    }
}
