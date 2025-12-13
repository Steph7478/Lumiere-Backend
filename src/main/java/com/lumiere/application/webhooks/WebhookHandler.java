package com.lumiere.application.webhooks;

public interface WebhookHandler {
    boolean supports(String eventType);

    void handle(WebhookEvent event);
}
