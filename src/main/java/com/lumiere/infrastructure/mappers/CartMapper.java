package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.vo.CartItem;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CartItemJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.CartJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.UserJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.product.ProductJpaRepository;
import com.lumiere.infrastructure.persistence.jpa.repositories.user.UserJpaRepository;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Context;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { CartItemMapper.class,
                UserMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper extends BaseMapper<Cart, CartJpaEntity> {

        @Mapping(target = "items", source = "items", qualifiedByName = "toDomainWithRepo")
        Cart toDomain(CartJpaEntity jpaEntity,
                        @Context CartItemMapper cartItemMapper,
                        @Context ProductJpaRepository productJpaRepository);

        @Mapping(target = "user", source = "domain.user.id", qualifiedByName = "loadUserRef")
        @Mapping(target = "items", source = "domain.items")
        CartJpaEntity toJpa(
                        Cart domain,
                        @Context CartItemMapper cartItemMapper,
                        @Context UserJpaRepository userJpaRepository,
                        @Context ProductJpaRepository productJpaRepository);

        @Named("loadUserRef")
        default UserJpaEntity loadUserReference(UUID userId, @Context UserJpaRepository userJpaRepository) {
                if (userId == null)
                        return null;
                return userJpaRepository.getReferenceById(userId);
        }

        default List<CartItemJpaEntity> mapCartItems(
                        List<CartItem> items,
                        @Context CartItemMapper cartItemMapper,
                        @Context ProductJpaRepository productJpaRepository) {

                return items.stream()
                                .map(item -> cartItemMapper.toJpa(item,
                                                productJpaRepository))
                                .collect(Collectors.toList());
        }
}
