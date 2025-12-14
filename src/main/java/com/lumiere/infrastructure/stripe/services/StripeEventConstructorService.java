package com.lumiere.infrastructure.stripe.services;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeEventConstructorService {

    @Value("${STRIPE_WEBHOOK_SECRET}")
    private String webhookSecret;

    public Event constructEvent(String payload, String signature)
            throws SignatureVerificationException {

        return Webhook.constructEvent(payload, signature, webhookSecret);
    }
}