package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Cart;
import com.lumiere.infrastructure.persistence.jpa.entities.CartItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.CartJpaEntity;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.UserJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.user.UserJpaRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { CartItemMapper.class, UserMapper.class
}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CartMapper implements BaseMapper<Cart, CartJpaEntity> {

        @Autowired
        protected UserJpaRepository userJpaRepository;

        @Mapping(target = "user", source = "user")
        @Mapping(target = "items", source = "items")
        public abstract Cart toDomain(CartJpaEntity jpaEntity);

        @Mapping(target = ".", source = "domain", qualifiedByName = "fullCartToJpa")
        public abstract CartJpaEntity toJpa(Cart domain,
                        CartItemMapper cartItemMapper);

        @Named("fullCartToJpa")
        protected CartJpaEntity mapCartToJpaWithLookups(
                        Cart domain,
                        CartItemMapper cartItemMapper) {

                UserJpaEntity userJpa = userJpaRepository
                                .getReferenceById(Objects.requireNonNull(domain.getUser().getId()));

                List<CartItemJpaEntity> jpaItems = domain.getItems().stream()
                                .map(domainItem -> {
                                        return cartItemMapper.toJpa(domainItem);
                                })
                                .collect(Collectors.toList());

                return new CartJpaEntity(
                                domain.getId(),
                                userJpa,
                                jpaItems);
        }
}