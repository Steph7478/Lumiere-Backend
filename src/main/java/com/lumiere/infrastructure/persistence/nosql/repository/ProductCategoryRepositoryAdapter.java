package com.lumiere.infrastructure.persistence.nosql.repository;

import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.repositories.NoSqlRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class ProductCategoryRepositoryAdapter implements NoSqlRepository<ProductCategory> {

    private final NoSqlRepository<ProductCategory> dataStore;

    public ProductCategoryRepositoryAdapter(NoSqlRepository<ProductCategory> dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void save(String id, ProductCategory product) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(product);
        dataStore.save(id, product);
    }

    @Override
    public ProductCategory findById(String id) {
        Objects.requireNonNull(id);
        return dataStore.findById(id);
    }

    @Override
    public void delete(String id) {
        Objects.requireNonNull(id);
        dataStore.delete(id);
    }

    @Override
    public List<ProductCategory> findBySubcategory(String subcategory) {
        return dataStore.findBySubcategory(subcategory);
    }

    @Override
    public List<ProductCategory> findByCategory(String category) {
        return dataStore.findByCategory(category);
    }
}