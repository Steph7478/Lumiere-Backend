package com.lumiere.application.exceptions.product;

import java.util.UUID;

import com.lumiere.application.exceptions.base.ApplicationException;

public class ProductNotFoundException extends ApplicationException {

    public ProductNotFoundException(UUID id) {
        super("Product not found with ID: " + id);

    }

}
