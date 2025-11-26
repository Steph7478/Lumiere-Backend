package com.lumiere.domain.repositories;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.lumiere.domain.entities.Product;
import com.lumiere.domain.repositories.base.BaseRepository;

public interface ProductRepository extends BaseRepository<Product> {
    List<Product> findAllByIdIn(Collection<UUID> ids);
}
