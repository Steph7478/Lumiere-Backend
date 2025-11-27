package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.readmodels.CartItemReadModel;
import com.lumiere.domain.readmodels.CartReadModel;
import com.lumiere.domain.vo.CartItem;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CartItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.CartJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.product.ProductJpaRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { CartItemMapper.class,
                UserMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CartMapper implements BaseMapper<Cart, CartJpaEntity> {

        @Autowired
        protected ProductJpaRepository productJpaRepository;

        @Autowired
        protected CartItemMapper cartItemMapper;

        @Mapping(target = "items", ignore = true)
        @Mapping(target = "user", source = "domain.user")
        public abstract CartJpaEntity mapToJpa(Cart domain);

        public CartReadModel toReadModel(Cart domain) {
                var productCache = loadProducts(domain.getItems());

                var items = domain.getItems().stream()
                                .map(i -> new CartItemReadModel(
                                                productCache.get(i.getProductId()),
                                                i.getQuantity()))
                                .toList();

                return new CartReadModel(
                                domain.getId(),
                                items,
                                domain.getCreatedAt(),
                                domain.getUpdatedAt());
        }

        protected Map<UUID, ProductJpaEntity> loadProducts(List<CartItem> items) {
                var ids = items.stream()
                                .map(CartItem::getProductId)
                                .collect(Collectors.toSet());

                return productJpaRepository.findAllByIdIn(Objects.requireNonNull(ids)).stream()
                                .collect(Collectors.toMap(ProductJpaEntity::getId, p -> p));
        }

        @AfterMapping
        protected void mapItems(Cart domain, @MappingTarget CartJpaEntity jpa) {
                var productCache = loadProducts(domain.getItems());

                var items = domain.getItems().stream()
                                .map(item -> {
                                        var itemJpa = cartItemMapper.toJpa(item, productCache);
                                        itemJpa.setCartReference(jpa);
                                        return itemJpa;
                                })
                                .toList();

                jpa.setItems(items);
        }
}
