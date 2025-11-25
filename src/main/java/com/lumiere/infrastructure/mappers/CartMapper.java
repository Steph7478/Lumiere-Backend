package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Cart;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CartJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.CartItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.product.ProductJpaRepository;
import com.lumiere.infrastructure.persistence.jpa.repositories.user.UserJpaRepository;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { CartItemMapper.class,
                UserMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CartMapper implements BaseMapper<Cart, CartJpaEntity> {

        @Autowired
        protected UserJpaRepository userJpaRepository;

        @Autowired
        private ProductJpaRepository productJpaRepository;

        @Autowired
        private CartItemMapper cartItemMapper;

        @Mapping(target = "user", source = "user")
        @Mapping(target = "items", source = "items")
        public abstract Cart toDomain(CartJpaEntity jpaEntity);

        @Mapping(target = "items", ignore = true)
        @Mapping(target = "user", source = "domain.user")
        @Mapping(target = "id", source = "domain.id")
        public abstract CartJpaEntity mapToJpa(Cart domain);

        @AfterMapping
        protected void mapItemsAndLink(
                        Cart domain,
                        @MappingTarget CartJpaEntity jpaEntity) {
                if (domain.getItems() == null || domain.getItems().isEmpty()) {
                        jpaEntity.setItems(List.of());
                        return;
                }

                Set<UUID> productIds = domain.getItems().stream()
                                .map(item -> item.getProductId())
                                .collect(Collectors.toSet());

                Map<UUID, ProductJpaEntity> productCache = productJpaRepository
                                .findAllByIdIn(Objects.requireNonNull(productIds))
                                .stream()
                                .collect(Collectors.toMap(ProductJpaEntity::getId, product -> product));

                List<CartItemJpaEntity> items = domain.getItems().stream()
                                .map(item -> {
                                        CartItemJpaEntity jpaItem = cartItemMapper.toJpa(item, productCache);
                                        jpaItem.setCartReference(jpaEntity);
                                        return jpaItem;
                                })
                                .toList();

                jpaEntity.setItems(items);
        }
}
