package com.lumiere.application.mappers.cart;

import com.lumiere.application.dtos.cart.command.add.AddCartInput;
import com.lumiere.application.dtos.cart.command.add.AddCartRequestData;
import com.lumiere.application.mappers.base.BaseMapper;
import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.readmodels.CartReadModel;
import com.lumiere.domain.services.CartService;
import com.lumiere.infrastructure.mappers.CartItemMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = { CartItemMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartReadModelMapper extends BaseMapper<Cart, CartReadModel> {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "items", source = "items")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    CartReadModel toDTO(Cart domain);

    Cart toEntity(AddCartInput toEntity);

    @ObjectFactory
    default Cart addProducts(Cart currentCart, AddCartRequestData reqData) {
        return CartService.addProducts(
                currentCart,
                reqData.items());
    }
}