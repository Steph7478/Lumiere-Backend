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
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { CartItemMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CartMapper implements BaseMapper<Cart, CartJpaEntity> {

        protected final CartItemMapper cartItemMapper;
        protected final UserJpaRepository userJpaRepository;
        protected final ProductJpaRepository productJpaRepository;

        protected CartMapper() {
                this.cartItemMapper = null;
                this.userJpaRepository = null;
                this.productJpaRepository = null;
        }

        public CartMapper(CartItemMapper cartItemMapper, UserJpaRepository userJpaRepository,
                        ProductJpaRepository productJpaRepository) {
                this.cartItemMapper = cartItemMapper;
                this.userJpaRepository = userJpaRepository;
                this.productJpaRepository = productJpaRepository;
        }

        @Mappings({
                        @Mapping(target = "id", source = "jpaEntity.id"),
                        @Mapping(target = "userId", source = "jpaEntity", qualifiedByName = "mapUserIdFromCart"),
                        @Mapping(target = "coupon", source = "jpaEntity.coupon"),
                        @Mapping(target = "items", source = "jpaEntity.items")
        })
        public abstract Cart toDomain(CartJpaEntity jpaEntity);

        @Named("mapUserIdFromCart")
        protected UUID mapUserIdFromCart(CartJpaEntity jpaEntity) {
                return Objects.requireNonNull(jpaEntity.getUserId().getId());
        }

        @Mapping(target = ".", source = "domain", qualifiedByName = "fullCartToJpa")
        public abstract CartJpaEntity toJpa(Cart domain);

        protected UserJpaEntity map(UUID userId) {
                return userJpaRepository.getReferenceById(Objects.requireNonNull(userId));
        }

        protected String map(Optional<String> optionalCoupon) {
                return optionalCoupon.orElse(null);
        }

        @Named("fullCartToJpa")
        public CartJpaEntity mapCartToJpaWithLookups(Cart domain) {

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