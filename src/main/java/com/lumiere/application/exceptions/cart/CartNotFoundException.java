package com.lumiere.application.exceptions.cart;

import com.lumiere.application.exceptions.base.ApplicationException;

public class CartNotFoundException extends ApplicationException {

    public CartNotFoundException() {
        super("Cart not found with ID");

    }

}
