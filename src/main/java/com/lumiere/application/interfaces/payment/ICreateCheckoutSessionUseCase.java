package com.lumiere.application.interfaces.payment;

import com.lumiere.application.dtos.payment.command.checkout.CreateCheckoutSessionOutput;
import com.lumiere.application.dtos.payment.command.checkout.CreateCheckoutSessionInput;

import reactor.core.publisher.Mono;

public interface ICreateCheckoutSessionUseCase {
        Mono<CreateCheckoutSessionOutput> execute(CreateCheckoutSessionInput input);
}