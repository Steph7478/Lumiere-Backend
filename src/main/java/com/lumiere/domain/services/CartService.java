package com.lumiere.domain.services;

import java.util.List;
import java.util.UUID;

import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.vo.CartItem;

public class CartService {

    public static Cart addProducts(Cart cart, List<CartItem> items) {

        Cart updatedCart = cart;

        for (CartItem item : items)
            updatedCart = updatedCart.addProduct(item.getProductId(), item.getQuantity());

        return updatedCart;
    }

    public static Cart removeProduct(Cart cart, UUID productId) {
        return cart.removeProduct(productId);
    }

    public static Cart createCart(User userId) {
        return Cart.createCart(userId);
    }
}