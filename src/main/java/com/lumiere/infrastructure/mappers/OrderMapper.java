package com.lumiere.infrastructure.mappers;

import java.util.List;
import java.util.Objects;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lumiere.domain.entities.Order;
import com.lumiere.domain.factories.OrderFactory;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.OrderItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.OrderJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.UserJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.user.UserJpaRepository;

@Mapper(componentModel = "spring", uses = { OrderItemMapper.class, UserMapper.class,
        OrderFactory.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrderMapper implements BaseMapper<Order, OrderJpaEntity> {

    @Autowired
    protected UserJpaRepository userJpaRepository;

    @Mapping(target = "id", source = "id")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "paymentId", source = "paymentId")
    @Mapping(target = "total", source = "total")
    @Mapping(target = "items", source = "items")
    @Mapping(target = "coupon", source = "coupon")
    public abstract Order toDomain(OrderJpaEntity jpaEntity);

    @Mapping(target = ".", source = "domain", qualifiedByName = "fullOrderToJpa")
    public abstract OrderJpaEntity toJpa(Order domain, OrderItemMapper orderItemMapper);

    @Named("fullOrderToJpa")
    protected OrderJpaEntity mapOrderToJpaWithLookups(Order domain, OrderItemMapper orderItemMapper) {

        UserJpaEntity userJpa = userJpaRepository
                .getReferenceById(Objects.requireNonNull(domain.getUser().getId()));

        List<OrderItemJpaEntity> jpaItems = domain.getItems().stream().map(domainItem -> {
            return orderItemMapper.toJpa(domainItem);
        }).toList();

        return new OrderJpaEntity(domain.getId(), userJpa, domain.getStatus(), domain.getPaymentId(), domain.getTotal(),
                jpaItems, domain.getCoupon());
    }
}
