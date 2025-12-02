package com.lumiere.application.services;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.order.command.add.AddItemOrderRequestData.OrderItemData;
import com.lumiere.domain.entities.Order;
import com.lumiere.domain.readmodels.ProductDetailReadModel;
import com.lumiere.domain.vo.OrderItem;

@Service
public class OrderItemAssemblerService {
    public OrderItem buildOrderItem(Order order, Map<UUID, ProductDetailReadModel> productMap,
            OrderItemData inputItem) {
        UUID productId = inputItem.productId();

        OrderItem existingItem = order.getItems().stream()
                .filter(item -> Objects.equals(item.getProductId(), productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            return new OrderItem(
                    existingItem.getProductId(),
                    existingItem.getName(),
                    inputItem.quantity(),
                    existingItem.getUnitPrice(),
                    existingItem.getCategory());
        } else {
            ProductDetailReadModel product = productMap.get(productId);
            return new OrderItem(
                    productId,
                    product.name(),
                    inputItem.quantity(),
                    product.price().getAmount(),
                    product.category());
        }
    }
}