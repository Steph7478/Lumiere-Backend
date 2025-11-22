package com.lumiere.domain.services;

import java.util.List;
import java.util.UUID;

import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.vo.CartItem;

public class CartService {

    public static Cart addProducts(Cart cart, List<CartItem> items) {
        if (items == null || items.isEmpty())
            return cart;

        Cart updatedCart = cart;

        for (CartItem item : items)
            updatedCart = updatedCart.addProduct(item.getProductId(), item.getQuantity());

        return updatedCart;
    }

    public static Cart removeProduct(Cart cart, List<UUID> productsId) {
        if (productsId == null || productsId.isEmpty())
            return cart;

        Cart updatedCart = cart;

        for (UUID productId : productsId)
            updatedCart = updatedCart.removeProduct(productId);

        return updatedCart;
    }

    public static Cart createCart(User user) {
        return Cart.createCart(user);
    }
}