package com.lumiere.application.usecases.payment;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import com.lumiere.application.dtos.payment.command.checkout.CreateCheckoutSessionInput;
import com.lumiere.application.dtos.payment.command.checkout.CreateCheckoutSessionOutput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.exceptions.order.OrderNotFoundException;
import com.lumiere.application.exceptions.payment.PaymentGatewayException;
import com.lumiere.application.interfaces.payment.ICreateCheckoutSessionUseCase;
import com.lumiere.domain.entities.Order;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.enums.StatusEnum.Status;
import com.lumiere.domain.repositories.OrderRepository;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.infrastructure.stripe.gateways.StripePaymentGateway;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class CreateCheckoutSessionUseCase implements ICreateCheckoutSessionUseCase {

    private final StripePaymentGateway gateway;
    private final OrderRepository orderRepo;
    private final UserRepository userRepo;

    protected CreateCheckoutSessionUseCase(
            StripePaymentGateway gateway,
            OrderRepository orderRepo,
            UserRepository userRepo) {
        this.gateway = gateway;
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    @Override
    @Retry(name = "paymentServiceRetry")
    @CircuitBreaker(name = "paymentServiceCB", fallbackMethod = "fallbackExecute")
    public Mono<CreateCheckoutSessionOutput> execute(CreateCheckoutSessionInput input) {

        Mono<Order> orderMono = Mono.fromCallable(() -> {
            User user = userRepo.findUserByAuthId(input.userId())
                    .orElseThrow(UserNotFoundException::new);

            return orderRepo.findByUserIdAndStatus(user.getId(), Status.IN_PROGRESS)
                    .orElseThrow(OrderNotFoundException::new);
        });

        Mono<CreateCheckoutSessionOutput> paymentMono = orderMono.flatMap(order -> Mono.fromCallable(() -> {
            Session session = gateway.createCustomCheckoutSession(
                    order,
                    input.data().successUrl(),
                    input.data().cancelUrl());
            return new CreateCheckoutSessionOutput(session.getUrl());
        })).onErrorMap(StripeException.class, e -> new PaymentGatewayException());

        return paymentMono.subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<CreateCheckoutSessionOutput> fallbackExecute(
            CreateCheckoutSessionInput input,
            Throwable t) {

        if (t instanceof UserNotFoundException || t instanceof OrderNotFoundException)
            return Mono.error(t);

        return Mono.error(PaymentGatewayException::new);
    }
}
