package com.lumiere.application.webhooks;

import java.util.Map;

public record WebhookEvent(
        String source,
        String type,
        Map<String, Object> data) {
}
