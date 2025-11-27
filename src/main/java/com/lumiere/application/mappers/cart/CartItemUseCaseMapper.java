package com.lumiere.application.mappers.cart;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.lumiere.domain.entities.Product;
import com.lumiere.domain.vo.CartItem;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemUseCaseMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "cartItem.quantity", target = "quantity")
    CartItem toCartItem(Product product, CartItem cartItem);
}
