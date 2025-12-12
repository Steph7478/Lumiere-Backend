package com.lumiere.infrastructure.stripe.adapters;

import com.lumiere.application.webhooks.WebhookEvent;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CheckoutSessionCompletedAdapter implements StripeEventAdapter {

    @Override
    public boolean supports(String eventType) {
        return "checkout.session.completed".equals(eventType);
    }

    @Override
    public WebhookEvent convert(Event event) {
        Session session = (Session) event.getDataObjectDeserializer().getObject().orElseThrow();

        var data = new HashMap<String, Object>();
        data.put("status", session.getPaymentStatus());
        data.put("orderId", session.getClientReferenceId());
        data.put("userId", session.getCustomer());
        data.put("createdAt", session.getCreated());
        data.put("paymentMethod", session.getPaymentMethodTypes());

        return new WebhookEvent(
                "stripe",
                "payment.succeeded",
                data);
    }
}
