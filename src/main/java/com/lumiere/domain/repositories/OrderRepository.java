package com.lumiere.domain.repositories;

import com.lumiere.domain.entities.Order;

public interface OrderRepository {
    Order save(Order order);
}
