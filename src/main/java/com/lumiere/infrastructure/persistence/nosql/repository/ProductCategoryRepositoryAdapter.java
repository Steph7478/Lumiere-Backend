package com.lumiere.infrastructure.persistence.nosql.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.repositories.NoSqlRepository;
import com.lumiere.infrastructure.persistence.nosql.entities.ProductCategoryEntity;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductCategoryRepositoryAdapter implements NoSqlRepository<ProductCategory> {

    private final ProductCategoryRepository redisRepository;

    @Override
    public void save(ProductCategory obj) {
        ProductCategoryEntity entity = new ProductCategoryEntity(obj);
        redisRepository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        redisRepository.deleteById(Objects.requireNonNull(id));
    }

    @Override
    public ProductCategory findById(UUID id) {
        return redisRepository.findById(Objects.requireNonNull(id))
                .map(ProductCategoryEntity::toDomain)
                .orElseThrow(() -> new java.util.NoSuchElementException("ProductCategory not found for ID: " + id));
    }

    @Override
    public List<ProductCategory> findBySubcategory(String subcategory) {
        List<ProductCategoryEntity> entities = redisRepository.findBySubcategoryName(subcategory);
        return convertToDomainList(entities);
    }

    @Override
    public List<ProductCategory> findByCategory(String category) {
        List<ProductCategoryEntity> entities = redisRepository.findByCategoryName(category);
        return convertToDomainList(entities);
    }

    @Override
    public List<ProductCategory> findByCategoryAndSubcategory(String category, String subcategory) {
        List<ProductCategoryEntity> entities = redisRepository.findByCategoryNameAndSubcategoryName(category,
                subcategory);
        return convertToDomainList(entities);
    }

    @Override
    public List<ProductCategory> findByIds(List<UUID> ids) {
        List<ProductCategoryEntity> entities = (List<ProductCategoryEntity>) redisRepository
                .findAllById(Objects.requireNonNull(ids));
        return convertToDomainList(entities);
    }

    private List<ProductCategory> convertToDomainList(List<ProductCategoryEntity> entities) {
        return entities.stream()
                .map(ProductCategoryEntity::toDomain)
                .collect(Collectors.toList());
    }
}