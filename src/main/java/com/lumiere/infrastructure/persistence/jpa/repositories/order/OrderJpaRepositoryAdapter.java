package com.lumiere.infrastructure.persistence.jpa.repositories.order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lumiere.domain.entities.Order;
import com.lumiere.domain.enums.StatusEnum.Status;
import com.lumiere.domain.repositories.OrderRepository;
import com.lumiere.infrastructure.mappers.OrderMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.OrderJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.base.BaseRepositoryAdapter;

import jakarta.persistence.EntityManager;

@Repository
public class OrderJpaRepositoryAdapter extends BaseRepositoryAdapter<Order, OrderJpaEntity> implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;
    private final OrderMapper orderMapper;

    protected OrderJpaRepositoryAdapter(OrderJpaRepository jpaRepository,
            OrderMapper mapper, EntityManager entityManager) {
        super(jpaRepository, mapper, entityManager);

        this.orderMapper = mapper;
        this.orderJpaRepository = jpaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findOrderByUserId(UUID id) {
        List<OrderJpaEntity> jpaEntities = orderJpaRepository.findByUserId(id);
        return jpaEntities.stream()
                .map(orderMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUserIdAndStatus(UUID userId, Status status) {
        return orderJpaRepository.existsByUserIdAndStatus(userId, status);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findByUserIdAndStatus(UUID userId, Status status) {
        return orderJpaRepository.findByUserIdAndStatus(userId, status).map(orderMapper::toDomain);

    }
}
