package com.lumiere.infrastructure.stripe;

import com.lumiere.domain.entities.Order;
import com.lumiere.domain.enums.CurrencyEnum.CurrencyType;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData.ProductData;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StripePaymentGateway {

    public Session createCustomCheckoutSession(
            Order order,
            String successUrl,
            String cancelUrl) throws StripeException {

        final CurrencyType targetCurrencyType = order.getCurrency();
        final String targetCurrencyCode = targetCurrencyType.name().toLowerCase();

        List<LineItem> lineItems = order.getItems().stream().map(item -> {
            BigDecimal priceInTargetCurrency = item.getUnitPrice();

            long unitAmountCents = priceInTargetCurrency
                    .multiply(BigDecimal.valueOf(100))
                    .longValue();

            PriceData priceData = PriceData.builder()
                    .setCurrency(targetCurrencyCode)
                    .setUnitAmount(unitAmountCents)
                    .setProductData(
                            ProductData.builder()
                                    .setName(item.getName())
                                    .build())
                    .build();

            return LineItem.builder()
                    .setQuantity((long) item.getQuantity())
                    .setPriceData(priceData)
                    .build();

        }).collect(Collectors.toList());

        SessionCreateParams params = SessionCreateParams.builder()
                .addAllLineItem(lineItems)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .build();

        return Session.create(params);
    }
}