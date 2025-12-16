package com.lumiere.presentation.controllers;

import com.lumiere.application.ports.WebhookPort;
import com.lumiere.application.webhooks.WebhookEvent;
import com.lumiere.infrastructure.stripe.dispatcher.StripeWebhookEventDispatcher;
import com.lumiere.infrastructure.stripe.services.StripeEventConstructorService;
import com.lumiere.presentation.routes.Routes;
import com.lumiere.shared.annotations.api.ApiVersion;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;

import io.swagger.v3.oas.annotations.Hidden;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Hidden
@RestController
public class StripeWebhookController {

    private final StripeWebhookEventDispatcher dispatcher;
    private final WebhookPort webhookPort;
    private final StripeEventConstructorService eventConstructorService;

    public StripeWebhookController(
            StripeWebhookEventDispatcher dispatcher,
            WebhookPort webhookPort,
            StripeEventConstructorService eventConstructorService) {
        this.dispatcher = dispatcher;
        this.webhookPort = webhookPort;
        this.eventConstructorService = eventConstructorService;
    }

    @ApiVersion("1")
    @PostMapping(Routes.PUBLIC.WEBHOOKS.STRIPE)
    public ResponseEntity<Void> handle(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature) throws SignatureVerificationException {

        Event event = eventConstructorService.constructEvent(payload, signature);

        WebhookEvent internal = dispatcher.dispatch(event);

        if (internal != null)
            webhookPort.handle(internal);

        return ResponseEntity.ok().build();
    }
}