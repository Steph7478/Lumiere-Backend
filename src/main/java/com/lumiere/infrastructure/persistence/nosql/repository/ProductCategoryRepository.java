package com.lumiere.infrastructure.persistence.nosql.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lumiere.infrastructure.persistence.nosql.entities.ProductCategoryEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategoryEntity, UUID> {

    List<ProductCategoryEntity> findBySubcategoryName(String subcategoryName);

    List<ProductCategoryEntity> findByCategoryName(String categoryName);

    List<ProductCategoryEntity> findByCategoryNameAndSubcategoryName(String categoryName, String subcategoryName);
}