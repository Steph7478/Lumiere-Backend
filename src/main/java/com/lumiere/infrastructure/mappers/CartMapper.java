package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.readmodels.CartItemReadModel;
import com.lumiere.domain.readmodels.CartReadModel;
import com.lumiere.infrastructure.persistence.jpa.entities.CartJpaEntity;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CartItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.product.ProductJpaRepository;
import com.lumiere.infrastructure.persistence.jpa.repositories.user.UserJpaRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { CartItemMapper.class,
                UserMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CartMapper implements BaseMapper<Cart, CartJpaEntity> {

        @Autowired
        protected UserJpaRepository userJpaRepository;

        @Autowired
        protected ProductJpaRepository productJpaRepository;

        @Autowired
        protected CartItemMapper cartItemMapper;

        @Mapping(target = "items", ignore = true)
        @Mapping(target = "user", source = "domain.user")
        public abstract CartJpaEntity mapToJpa(Cart domain);

        @BeforeMapping
        protected Map<UUID, ProductJpaEntity> loadProductsForCart(Cart domain) {
                return productJpaRepository.findAllByIdIn(Objects.requireNonNull(
                                domain.getItems().stream()
                                                .map(i -> i.getProductId())
                                                .collect(Collectors.toSet())))
                                .stream()
                                .collect(Collectors.toMap(ProductJpaEntity::getId, p -> p));
        }

        @AfterMapping
        protected void mapItemsAndLink(Cart domain, @MappingTarget CartJpaEntity jpaEntity) {
                Map<UUID, ProductJpaEntity> productCache = loadProductsForCart(domain);

                jpaEntity.setItems(domain.getItems().stream()
                                .map(i -> {
                                        CartItemJpaEntity itemJpa = cartItemMapper.toJpa(i, productCache);
                                        itemJpa.setCartReference(jpaEntity);
                                        return itemJpa;
                                })
                                .collect(Collectors.toList()));
        }

        public CartReadModel toReadModel(Cart domain) {
                Map<UUID, ProductJpaEntity> productCache = loadProductsForCart(domain);

                List<CartItemReadModel> items = Optional.ofNullable(domain.getItems())
                                .orElse(List.of())
                                .stream()
                                .map(item -> new CartItemReadModel(productCache.get(item.getProductId()),
                                                item.getQuantity()))
                                .collect(Collectors.toList());

                return new CartReadModel(domain.getId(), items, domain.getCreatedAt(), domain.getUpdatedAt());
        }

}
