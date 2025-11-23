package com.lumiere.application.mappers.cart;

import com.lumiere.application.dtos.cart.command.add.AddCartInput;
import com.lumiere.application.dtos.cart.command.add.AddMultipleItemsRequestData;
import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.services.CartService;
import com.lumiere.infrastructure.mappers.CartItemMapper;

import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = { CartItemMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddCartReadModel {

    Cart toEntity(AddCartInput toEntity);

    @ObjectFactory
    default Cart addProducts(Cart currentCart, AddMultipleItemsRequestData reqData) {
        return CartService.addProducts(
                currentCart,
                reqData.items());
    }
}