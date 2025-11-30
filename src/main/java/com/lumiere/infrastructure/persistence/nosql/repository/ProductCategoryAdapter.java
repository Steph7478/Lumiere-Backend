package com.lumiere.infrastructure.persistence.nosql.repository;

import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.repositories.NoSqlRepository;
import com.lumiere.infrastructure.persistence.nosql.entities.ProductCategoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@SuppressWarnings("null")
public class ProductCategoryAdapter implements NoSqlRepository<ProductCategory> {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryAdapter(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public void save(ProductCategory entity) {
        ProductCategoryEntity entityToSave = new ProductCategoryEntity(entity);
        productCategoryRepository.save(entityToSave);
    }

    @Override
    public void delete(UUID id) {
        productCategoryRepository.deleteById(id);
    }

    @Override
    public ProductCategory findById(UUID id) {
        ProductCategoryEntity entity = productCategoryRepository.findById(id).orElse(null);
        return entity != null ? entity.toDomain() : null;
    }

    @Override
    public List<ProductCategory> findBySubcategory(String subcategory) {
        return productCategoryRepository.findBySubcategoryName(subcategory).stream()
                .map(ProductCategoryEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductCategory> findByCategory(String category) {
        return productCategoryRepository.findByCategoryName(category).stream()
                .map(ProductCategoryEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductCategory> findByCategoryAndSubcategory(String category, String subcategory) {
        return productCategoryRepository.findByCategoryNameAndSubcategoryName(category, subcategory).stream()
                .map(ProductCategoryEntity::toDomain)
                .collect(Collectors.toList());
    }
}