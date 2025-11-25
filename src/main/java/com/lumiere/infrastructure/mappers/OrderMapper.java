package com.lumiere.infrastructure.mappers;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lumiere.domain.entities.Order;
import com.lumiere.domain.readmodels.OrderReadModel;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.OrderItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.OrderJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.product.ProductJpaRepository;
import com.lumiere.infrastructure.persistence.jpa.repositories.user.UserJpaRepository;

@Mapper(componentModel = "spring", uses = { OrderItemMapper.class,
                UserMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrderMapper implements BaseMapper<Order, OrderJpaEntity> {

        @Autowired
        protected UserJpaRepository userJpaRepository;

        @Autowired
        protected ProductJpaRepository productJpaRepository;

        @Autowired
        protected OrderItemMapper orderItemMapper;

        @Mapping(target = "id", source = "id")
        @Mapping(target = "user", source = "user")
        @Mapping(target = "status", source = "status")
        @Mapping(target = "paymentId", source = "paymentId")
        @Mapping(target = "total", source = "total")
        @Mapping(target = "items", source = "items")
        @Mapping(target = "coupon", source = "coupon")
        public abstract Order toDomain(OrderJpaEntity jpaEntity);

        @Mapping(target = "items", ignore = true)
        @Mapping(target = "total", source = "domain.total")
        @Mapping(target = "user", source = "domain.user")
        @Mapping(target = "paymentId", source = "domain.paymentId")
        @Mapping(target = "coupon", source = "domain.coupon")
        @Mapping(target = "id", source = "domain.id")
        public abstract OrderJpaEntity mapToJpa(Order domain);

        @AfterMapping
        protected void mapItemsAndLink(Order domain, @MappingTarget OrderJpaEntity jpaEntity) {
                if (domain.getItems() == null || domain.getItems().isEmpty())
                        return;

                Map<UUID, ProductJpaEntity> productCache = loadProductsForCart(domain);

                jpaEntity.setItems(domain.getItems().stream()
                                .map(i -> {
                                        OrderItemJpaEntity itemJpa = orderItemMapper.toJpa(i, productCache);
                                        itemJpa.setOrderReference(jpaEntity);
                                        return itemJpa;
                                })
                                .collect(Collectors.toList()));
        }

        protected Map<UUID, ProductJpaEntity> loadProductsForCart(Order domain) {
                return productJpaRepository.findAllByIdIn(Objects.requireNonNull(
                                domain.getItems().stream()
                                                .map(i -> i.getProductId())
                                                .collect(Collectors.toSet())))
                                .stream()
                                .collect(Collectors.toMap(ProductJpaEntity::getId, p -> p));
        }

        @Mapping(target = "id", source = "id")
        @Mapping(target = "status", source = "status")
        @Mapping(target = "total", source = "total")
        @Mapping(target = "items", source = "items")
        @Mapping(target = "coupon", source = "coupon")
        public abstract OrderReadModel toReadModel(Order order);
}
