package com.lumiere.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.lumiere.domain.entities.Order;
import com.lumiere.domain.readmodels.OrderReadModel;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.OrderJpaEntity;

@Mapper(componentModel = "spring", uses = { OrderItemMapper.class,
                UserMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrderMapper implements BaseMapper<Order, OrderJpaEntity> {

        @Mapping(target = "currency", expression = "java(domain.getCurrency().name())")
        @Mapping(target = "user", source = "domain.user")
        public abstract OrderJpaEntity toJpa(Order domain, Object... context);

        @Mapping(target = "id", source = "id")
        @Mapping(target = "status", source = "status")
        @Mapping(target = "total", source = "total")
        @Mapping(target = "items", source = "items")
        @Mapping(target = "coupon", source = "coupon")
        @Mapping(target = "currency", source = "currency")
        public abstract OrderReadModel toReadModel(Order order);
}
