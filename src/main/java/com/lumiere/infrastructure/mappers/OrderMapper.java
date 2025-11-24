package com.lumiere.infrastructure.mappers;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.mapstruct.Context;
import org.mapstruct.EnumMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.lumiere.domain.entities.Order;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.OrderItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.OrderJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.UserJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.product.ProductJpaRepository;
import com.lumiere.infrastructure.persistence.jpa.repositories.user.UserJpaRepository;

@Mapper(componentModel = "spring", uses = { OrderItemMapper.class,
                UserMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper extends BaseMapper<Order, OrderJpaEntity> {

        // ----------------------------
        // DOMAIN ← JPA (toDomain)
        // ----------------------------
        @Override
        @Mapping(target = "id", source = "id")
        @Mapping(target = "user", source = "user")
        @Mapping(target = "status", source = "status")
        @EnumMapping(nameTransformationStrategy = "case", configuration = "IDENTITY")
        @Mapping(target = "paymentId", source = "paymentId")
        @Mapping(target = "total", source = "total")
        @Mapping(target = "items", source = "items")
        @Mapping(target = "coupon", source = "coupon")
        Order toDomain(OrderJpaEntity jpaEntity);

        @Mapping(target = ".", source = "domain", qualifiedByName = "fullOrderToJpa")
        OrderJpaEntity toJpa(
                        Order domain,
                        @Context UserJpaRepository userJpaRepository,
                        @Context ProductJpaRepository productJpaRepository,
                        @Context OrderItemMapper orderItemMapper);

        @Named("fullOrderToJpa")
        default OrderJpaEntity fullOrderToJpa(
                        Order domain,
                        @Context UserJpaRepository userJpaRepository,
                        @Context ProductJpaRepository productJpaRepository,
                        @Context OrderItemMapper orderItemMapper) {

                UserJpaEntity userJpa = userJpaRepository.getReferenceById(
                                Objects.requireNonNull(domain.getUser().getId()));

                List<OrderItemJpaEntity> jpaItems = domain.getItems().stream()
                                .map(item -> orderItemMapper.toJpa(item, productJpaRepository))
                                .collect(Collectors.toList());

                return new OrderJpaEntity(
                                domain.getId(),
                                userJpa,
                                domain.getStatus(),
                                domain.getPaymentId(),
                                domain.getTotal(),
                                jpaItems,
                                domain.getCoupon());
        }
}
