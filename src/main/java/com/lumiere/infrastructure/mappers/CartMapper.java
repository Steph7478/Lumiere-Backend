package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Cart;
import com.lumiere.infrastructure.persistence.jpa.entities.CartJpaEntity;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
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

import java.util.Map;
import java.util.Objects;
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

        @Mapping(target = "items", ignore = true)
        @Mapping(target = "user", source = "domain.user")
        @Mapping(target = "id", source = "domain.id")
        public abstract CartJpaEntity mapToJpa(Cart domain);

        @AfterMapping
        protected void mapItemsAndLink(Cart domain, @MappingTarget CartJpaEntity jpaEntity) {
                if (domain.getItems() == null || domain.getItems().isEmpty())
                        return;

                Map<UUID, ProductJpaEntity> productCache = productJpaRepository
                                .findAllByIdIn(Objects.requireNonNull(domain.getItems()
                                                .stream()
                                                .map(i -> i.getProductId())
                                                .collect(Collectors.toSet())))
                                .stream()
                                .collect(Collectors.toMap(ProductJpaEntity::getId, p -> p));

                jpaEntity.setItems(domain.getItems().stream()
                                .map(i -> {
                                        CartItemJpaEntity itemJpa = cartItemMapper.toJpa(i, productCache);
                                        itemJpa.setCartReference(jpaEntity);
                                        return itemJpa;
                                })
                                .collect(Collectors.toList()));
        }
}
