package com.lumiere.application.usecases.payment;

import org.springframework.stereotype.Service;
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

@Service
public class CreateCheckoutSessionUseCase implements ICreateCheckoutSessionUseCase {

    private final StripePaymentGateway gateway;
    private final OrderRepository orderRepo;
    private final UserRepository userRepo;

    protected CreateCheckoutSessionUseCase(StripePaymentGateway gateway, OrderRepository orderRepo,
            UserRepository userRepo) {
        this.gateway = gateway;
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    @Override
    public CreateCheckoutSessionOutput execute(CreateCheckoutSessionInput input) {
        User user = userRepo.findUserByAuthId(input.userId()).orElseThrow(UserNotFoundException::new);

        Order order = orderRepo.findByUserIdAndStatus(user.getId(), Status.IN_PROGRESS)
                .orElseThrow(OrderNotFoundException::new);

        Session stripeSession;

        try {
            stripeSession = gateway.createCustomCheckoutSession(
                    order,
                    input.data().successUrl(),
                    input.data().cancelUrl());
        } catch (StripeException e) {
            throw new PaymentGatewayException();
        }

        String checkoutUrl = stripeSession.getUrl();

        return new CreateCheckoutSessionOutput(checkoutUrl);
    }
}