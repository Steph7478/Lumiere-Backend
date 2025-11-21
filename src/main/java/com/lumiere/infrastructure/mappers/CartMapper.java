package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Cart;
import com.lumiere.infrastructure.persistence.jpa.entities.CartJpaEntity;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CartItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.UserJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.product.ProductJpaRepository;
import com.lumiere.infrastructure.persistence.jpa.repositories.user.UserJpaRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { CartItemMapper.class, UserMapper.class
}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper extends BaseMapper<Cart, CartJpaEntity> {

        Cart toDomain(CartJpaEntity jpaEntity);

        @Mapping(target = ".", source = "domain", qualifiedByName = "fullCartToJpa")
        CartJpaEntity toJpa(Cart domain,
                        UserJpaRepository userJpaRepository,
                        ProductJpaRepository productJpaRepository,
                        CartItemMapper cartItemMapper);

        @Named("fullCartToJpa")
        default CartJpaEntity mapCartToJpaWithLookups(
                        Cart domain,
                        UserJpaRepository userJpaRepository,
                        ProductJpaRepository productJpaRepository,
                        CartItemMapper cartItemMapper) {

                UserJpaEntity userJpa = userJpaRepository.getReferenceById(
                                Objects.requireNonNull(domain.getUser().getId()));

                UUID cartId = Objects.requireNonNull(domain.getId());

                CartJpaEntity cartJpaReference = new CartJpaEntity(
                                cartId,
                                userJpa,
                                domain.getCoupon().orElse(null),
                                null);

                List<CartItemJpaEntity> jpaItems = domain.getItems().stream()
                                .map(domainItem -> {
                                        ProductJpaEntity productJpa = productJpaRepository.getReferenceById(
                                                        Objects.requireNonNull(domainItem.getProductId()));

                                        return cartItemMapper.toJpa(domainItem, cartJpaReference, productJpa);
                                })
                                .collect(Collectors.toList());

                return new CartJpaEntity(
                                cartId,
                                userJpa,
                                domain.getCoupon().orElse(null),
                                jpaItems);
        }

        default String map(Optional<String> optional) {
                return optional != null ? optional.orElse(null) : null;
        }
}