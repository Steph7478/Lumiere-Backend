package com.lumiere.application.ports;

import com.lumiere.application.webhooks.WebhookEvent;

public interface WebhookPort {
    void handle(WebhookEvent event);
}
