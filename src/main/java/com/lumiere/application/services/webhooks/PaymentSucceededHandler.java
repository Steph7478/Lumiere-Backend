package com.lumiere.application.services.webhooks;

import com.lumiere.application.usecases.payment.PaymentSucceededUseCase;
import com.lumiere.application.webhooks.WebhookEvent;
import com.lumiere.application.webhooks.WebhookHandler;
import org.springframework.stereotype.Service;

@Service
public class PaymentSucceededHandler implements WebhookHandler {

    private final PaymentSucceededUseCase useCase;

    public PaymentSucceededHandler(PaymentSucceededUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public boolean supports(String eventType) {
        return "payment.succeeded".equals(eventType);
    }

    @Override
    public void handle(WebhookEvent event) {
        useCase.execute(event);
    }
}
