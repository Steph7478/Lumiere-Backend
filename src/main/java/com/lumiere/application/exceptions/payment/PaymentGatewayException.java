package com.lumiere.application.exceptions.payment;

import com.lumiere.application.exceptions.base.ApplicationException;

public class PaymentGatewayException extends ApplicationException {
    public PaymentGatewayException() {
        super("Failed to initiate the payment process. Please try again.");

    }
}
