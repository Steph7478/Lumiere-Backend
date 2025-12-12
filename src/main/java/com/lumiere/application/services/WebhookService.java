package com.lumiere.application.services;

import com.lumiere.application.ports.WebhookPort;
import com.lumiere.application.usecases.payment.PaymentSucceededUseCase;
import com.lumiere.application.webhooks.WebhookEvent;
import org.springframework.stereotype.Service;

@Service
public class WebhookService implements WebhookPort {

    private final PaymentSucceededUseCase paymentSucceededUseCase;

    public WebhookService(
            PaymentSucceededUseCase paymentSucceededUseCase) {
        this.paymentSucceededUseCase = paymentSucceededUseCase;
    }

    @Override
    public void handle(WebhookEvent event) {
        paymentSucceededUseCase.execute(event);

    }
}
