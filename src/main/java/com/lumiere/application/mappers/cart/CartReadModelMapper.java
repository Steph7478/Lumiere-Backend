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
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.readmodels.CartItemReadModel;
import com.lumiere.domain.readmodels.CartReadModel;
import com.lumiere.domain.vo.CartItem;
import com.lumiere.infrastructure.mappers.CartMapper;
import com.lumiere.infrastructure.mappers.ProductMapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { ProductMapper.class,
        CartMapper.class })
public interface CartReadModelMapper {

    @Mapping(target = "items", source = "cart.items")
    CartReadModel toReadModel(Cart cart, @Context Map<UUID, Product> productCache);

    List<CartItemReadModel> toCartItemReadModel(Set<CartItem> items, @Context Map<UUID, Product> productCache);

    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "product", source = "productId")
    CartItemReadModel toCartItemReadModel(CartItem cartItem, @Context Map<UUID, Product> productCache);

    default Product map(UUID productId, @Context Map<UUID, Product> productCache) {
        return productCache.get(productId);
    }

}