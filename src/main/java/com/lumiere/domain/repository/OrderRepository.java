package com.lumiere.domain.repository;

import com.lumiere.domain.entity.Order;

public interface OrderRepository {
    Order save(Order order);
}
