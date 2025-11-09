package com.lumiere.infrastructure.persistence.nosql.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.repositories.NoSqlRepository;

@Repository
public class ProductCategoryRepositoryAdapter implements NoSqlRepository<ProductCategory> {

    private final RedisJsonRepository repository;

    public ProductCategoryRepositoryAdapter(RedisJsonRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(String id, ProductCategory product) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(product);

        repository.save(id, product);
        repository.addToSet("subcategory:" + product.getSubcategory(), id);
        repository.addToSet("category:" + product.getCategory(), product.getSubcategory());
    }

    @Override
    public ProductCategory search(String id, Class<ProductCategory> clazz) {
        Objects.requireNonNull(id);
        return repository.search(id);
    }

    @Override
    public void delete(String id) {
        Objects.requireNonNull(id);
        repository.delete(id);
    }

    public List<ProductCategory> findBySubcategory(String subcategory) {
        Set<String> ids = repository.getSetMembers("subcategory:" + subcategory);
        return ids.stream()
                .map(repository::search)
                .filter(Objects::nonNull)
                .toList();
    }

    public List<ProductCategory> findByCategory(String category) {
        Set<String> subcategories = repository.getSetMembers("category:" + category);
        List<ProductCategory> result = new ArrayList<>();
        for (String sub : subcategories) {
            result.addAll(findBySubcategory(sub));
        }
        return result;
    }
}
