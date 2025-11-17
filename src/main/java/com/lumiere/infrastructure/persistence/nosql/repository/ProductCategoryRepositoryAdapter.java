package com.lumiere.infrastructure.persistence.nosql.repository;

import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.repositories.NoSqlRepository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Primary
@Repository
public class ProductCategoryRepositoryAdapter implements NoSqlRepository<ProductCategory> {

    private final NoSqlRepository<ProductCategory> dataStore;

    public ProductCategoryRepositoryAdapter(
            NoSqlRepository<ProductCategory> dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void save(ProductCategory product) {
        Objects.requireNonNull(product.getId());
        Objects.requireNonNull(product);
        dataStore.save(product);
    }

    @Override
    public ProductCategory findById(UUID id) {
        Objects.requireNonNull(id);
        return dataStore.findById(id);
    }

    @Override
    public void delete(UUID id) {
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

    @Override
    public List<ProductCategory> findByIds(List<UUID> ids) {
        return dataStore.findByIds(ids);
    }
}