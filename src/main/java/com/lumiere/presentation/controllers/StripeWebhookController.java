package com.lumiere.presentation.controllers;

import com.lumiere.application.ports.WebhookPort;
import com.lumiere.application.webhooks.WebhookEvent;
import com.lumiere.infrastructure.stripe.dispatcher.StripeWebhookEventDispatcher;
import com.lumiere.presentation.routes.Routes;
import com.lumiere.shared.annotations.api.ApiVersion;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StripeWebhookController {

    private final StripeWebhookEventDispatcher dispatcher;
    private final WebhookPort webhookPort;

    @Value("${STRIPE_WEBHOOK_SECRET}")
    private String webhookSecret;

    public StripeWebhookController(
            StripeWebhookEventDispatcher dispatcher,
            WebhookPort webhookPort) {
        this.dispatcher = dispatcher;
        this.webhookPort = webhookPort;
    }

    @ApiVersion("1")
    @PostMapping(Routes.PUBLIC.WEBHOOKS.STRIPE)
    public ResponseEntity<Void> handle(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature) throws SignatureVerificationException {

        Event event = Webhook.constructEvent(payload, signature, webhookSecret);
        WebhookEvent internal = dispatcher.dispatch(event);

        if (internal != null)
            webhookPort.handle(internal);

        return ResponseEntity.ok().build();

    }
}
