package com.lumiere.domain.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.lumiere.domain.entities.Cart;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.vo.CartItem;

public class CartFactory {

    public static Cart from(UUID id, User user, List<CartItem> items) {
        return new Cart(id, user, items);
    }

    public static Cart create(User user) {
        return new Cart(UUID.randomUUID(), user, new ArrayList<>());
    }
}