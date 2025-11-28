package com.lumiere.domain.services;

import java.util.List;
import java.util.UUID;

import com.lumiere.domain.entities.Order;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.enums.CurrencyEnum.CurrencyType;
import com.lumiere.domain.factories.OrderFactory;
import com.lumiere.domain.vo.OrderItem;

public abstract class OrderService {

    public static Order createOrder(User user, List<OrderItem> items, CurrencyType currency) {
        return OrderFactory.create(UUID.randomUUID(), user, items, null, currency);
    }

    public static Order removeProduct(Order order, UUID productId) {
        return order.removeItem(productId);
    }

    public static Order useCoupon(Order order, String coupon) {
        return order.useCoupon(coupon);
    }

    public static Order markAsPaid(Order order, UUID paymentId) {
        return order.markAsPaid(paymentId);
    }
}
