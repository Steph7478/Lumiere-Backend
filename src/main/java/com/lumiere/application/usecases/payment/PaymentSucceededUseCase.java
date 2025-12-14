package com.lumiere.application.usecases.payment;

import com.lumiere.application.exceptions.order.OrderNotFoundException;
import com.lumiere.application.webhooks.WebhookEvent;
import com.lumiere.domain.entities.Order;
import com.lumiere.domain.entities.Payment;
import com.lumiere.domain.enums.PaymentMethodEnum;
import com.lumiere.domain.factories.PaymentFactory;
import com.lumiere.domain.repositories.OrderRepository;
import com.lumiere.domain.repositories.PaymentRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class PaymentSucceededUseCase {

    private final PaymentRepository paymentRepo;
    private final OrderRepository orderRepo;

    public PaymentSucceededUseCase(
            PaymentRepository paymentRepo,
            OrderRepository orderRepo) {
        this.paymentRepo = paymentRepo;
        this.orderRepo = orderRepo;
    }

    public void execute(WebhookEvent event) {

        Map<String, Object> d = event.data();

        String paymentId = d.get("paymentId").toString();
        Order order = orderRepo.findById(
                UUID.fromString(d.get("orderId").toString()))
                .orElseThrow(OrderNotFoundException::new);

        Object pm = d.get("paymentMethod");
        if (!(pm instanceof List<?> list) || list.isEmpty()) {
            throw new IllegalStateException("Invalid payment method");
        }

        PaymentMethodEnum method = switch (list.get(0).toString()) {
            case "pix" -> PaymentMethodEnum.PIX;
            case "boleto" -> PaymentMethodEnum.BOLETO;
            case "card" -> PaymentMethodEnum.CREDIT_CARD;
            default -> throw new IllegalStateException("Unsupported payment method");
        };

        LocalDateTime paidAt = Instant
                .ofEpochSecond(((Number) d.get("createdAt")).longValue())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Order paidOrder = order.markAsPaid(paymentId);
        Payment payment = PaymentFactory.create(paymentId, method, paidAt);

        orderRepo.save(paidOrder);
        paymentRepo.save(payment);
    }
}