package com.lumiere.application.exceptions.order;

import com.lumiere.application.exceptions.base.ApplicationException;

public class OrderNotFoundException extends ApplicationException {

    public OrderNotFoundException() {
        super("Order not found with ID");

    }

}
