package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Cart;
import com.lumiere.infrastructure.persistence.jpa.entities.CartJpaEntity;
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

@Mapper(componentModel = "spring", uses = { CartItemMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

        default String map(Optional<String> optional) {
                return optional != null ? optional.orElse(null) : null;
        }

        default Optional<String> map(String value) {
                return Optional.ofNullable(value);
        }

        @Mapping(target = "id", source = "jpaEntity.id")
        @Mapping(target = "userId", source = "jpaEntity", qualifiedByName = "mapUserIdFromCart")
        @Mapping(target = "items", source = "jpaEntity.items")
        Cart toDomain(CartJpaEntity jpaEntity);

        @Named("mapUserIdFromCart")
        default UUID mapUserIdFromCart(CartJpaEntity jpaEntity) {
                return Objects.requireNonNull(jpaEntity.getUserId().getId());
        }

        @Mapping(target = "userId", ignore = true)
        @Mapping(target = "id", ignore = true)
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
                                Objects.requireNonNull(domain.getUserId()));

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
}