package com.lumiere.domain.services;

import java.util.List;
import java.util.UUID;
import com.lumiere.domain.entities.Coupon;
import com.lumiere.domain.entities.Order;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.enums.CurrencyEnum.CurrencyType;
import com.lumiere.domain.factories.OrderFactory;
import com.lumiere.domain.vo.OrderItem;

public abstract class OrderService {
    public static Order createOrder(User user, List<OrderItem> items, CurrencyType currency) {
        return OrderFactory.create(UUID.randomUUID(), user, items, null, currency);
    }

    public static Order addProducts(Order order, List<OrderItem> items) {
        if (items == null || items.isEmpty())
            return order;

        Order updatedOrder = order;

        for (OrderItem item : items) {
            updatedOrder = updatedOrder.addItem(item.getProductId(), item.getName(), item.getQuantity(),
                    item.getUnitPrice());
        }

        return updatedOrder;
    }

    public static Order removeProducts(Order order, List<OrderItem> items) {
        if (items == null || items.isEmpty())
            return order;

        Order updatedOrder = order;

        for (OrderItem item : items) {
            updatedOrder = updatedOrder.removeItem(item.getProductId());
        }

        return updatedOrder;
    }

    public static Order useCoupon(Order order, Coupon coupon) {
        if (!isCouponApplicableToOrder(coupon, order)) {
            throw new IllegalArgumentException(
                    "The coupon category (" + coupon.getCategory() + ") is not valid for the items in this order.");
        }
        return order.useCoupon(coupon);
    }

    private static boolean isCouponApplicableToOrder(Coupon coupon, Order order) {
        if (coupon.getCategory() == null)
            return true;

        for (OrderItem item : order.getItems()) {
            Category productCategory = item.getCategory();
            if (productCategory != null && productCategory.equals(coupon.getCategory())) {
                return true;
            }
        }

        return false;
    }

    public static Order markAsPaid(Order order, UUID paymentId) {
        return order.markAsPaid(paymentId);
    }
}