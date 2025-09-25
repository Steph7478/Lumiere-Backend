package com.lumiere.domain.repositories;

import com.lumiere.domain.entities.Product;

public interface ProductRepository {
    Product save(Product product);
}
