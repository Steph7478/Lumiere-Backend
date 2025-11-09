package com.lumiere.infrastructure.persistence.nosql.repository;

import org.springframework.stereotype.Repository;

import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.repositories.NoSqlRepository;

@Repository
public class ProductCategoryRepositoryAdapter implements NoSqlRepository<ProductCategory> {

    private final RedisJsonRepository<ProductCategory> repository;

    public ProductCategoryRepositoryAdapter(RedisJsonRepository<ProductCategory> repository) {
        this.repository = repository;
    }

    @Override
    public void save(String id, ProductCategory obj) {
        repository.save(id, obj);
    }

    @Override
    public ProductCategory search(String id, Class<ProductCategory> clazz) {
        return repository.search(id, clazz);
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }
}
