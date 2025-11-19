package com.lumiere.infrastructure.persistence.jpa.repositories.order;

import com.lumiere.domain.entities.Order;
import com.lumiere.domain.repositories.OrderRepository;
import com.lumiere.infrastructure.mappers.OrderMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.OrderJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.base.BaseRepositoryAdapter;

import jakarta.persistence.EntityManager;

public class OrderJpaRepositoryAdapter extends BaseRepositoryAdapter<Order, OrderJpaEntity> implements OrderRepository {

    protected OrderJpaRepositoryAdapter(OrderJpaRepository jpaRepository,
            OrderMapper mapper, EntityManager entityManager) {
        super(jpaRepository, mapper, entityManager, OrderJpaEntity.class);
    }

}
