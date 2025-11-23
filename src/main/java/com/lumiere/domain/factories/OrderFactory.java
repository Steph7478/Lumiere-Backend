package com.lumiere.domain.factories;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.lumiere.domain.entities.Order;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.enums.StatusEnum.Status;
import com.lumiere.domain.vo.OrderItem;

public class OrderFactory {

    public static Order from(
            UUID id,
            User user,
            Status status,
            UUID paymentId,
            BigDecimal total,
            List<OrderItem> items,
            String coupon) {

        return new Order(id, user, status, paymentId, total, items, coupon);
    }

    public static Order create(
            UUID id,
            User user,
            List<OrderItem> items,
            String coupon) {

        BigDecimal calculatedTotal = calculateTotal(items);

        return new Order(id, user, Status.IN_PROGRESS, null, calculatedTotal, items, coupon);
    }

    private static BigDecimal calculateTotal(List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(OrderItem::calculateSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}