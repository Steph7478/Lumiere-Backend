package com.lumiere.application.mappers.cart;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.readmodels.CartItemReadModel;
import com.lumiere.domain.readmodels.CartReadModel;
import com.lumiere.domain.readmodels.ProductDetailReadModel;
import com.lumiere.domain.vo.CartItem;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartReadModelMapper {

        @Mapping(target = "items", source = "cart.items")
        CartReadModel toReadModel(
                        Cart cart,
                        @Context Map<String, ProductDetailReadModel> productCache);

        List<CartItemReadModel> toCartItemReadModel(
                        Set<CartItem> items,
                        @Context Map<String, ProductDetailReadModel> productCache);

        @Mapping(target = "quantity", source = "quantity")
        @Mapping(target = "product", source = "productId")
        CartItemReadModel toCartItemReadModel(
                        CartItem cartItem,
                        @Context Map<String, ProductDetailReadModel> productCache);

        default ProductDetailReadModel map(
                        UUID productId,
                        @Context Map<String, ProductDetailReadModel> productCache) {
                return productCache.get(productId.toString());
        }
}
