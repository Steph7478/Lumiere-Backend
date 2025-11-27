package com.lumiere.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.readmodels.CartReadModel;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CartJpaEntity;

@Mapper(componentModel = "spring", uses = { CartItemMapper.class,
        UserMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CartMapper implements BaseMapper<Cart, CartJpaEntity> {

    @Mapping(target = "user", source = "domain.user")
    public abstract CartJpaEntity toJpa(Cart domain, Object... context);
}
