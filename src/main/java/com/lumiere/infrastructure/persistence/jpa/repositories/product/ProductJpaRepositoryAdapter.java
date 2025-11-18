package com.lumiere.infrastructure.persistence.jpa.repositories.product;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lumiere.domain.entities.Product;
import com.lumiere.domain.repositories.ProductRepository;
import com.lumiere.infrastructure.mappers.ProductMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.base.BaseRepositoryAdapter;

import jakarta.persistence.EntityManager;

@Repository
public class ProductJpaRepositoryAdapter extends BaseRepositoryAdapter<Product, ProductJpaEntity>
        implements ProductRepository {

    public ProductJpaRepositoryAdapter(
            ProductJpaRepository productRepo,
            ProductMapper productMapper,
            EntityManager entityManager) {
        super(productRepo, productMapper, entityManager, ProductJpaEntity.class);
    }

    @Override
    @Cacheable(value = "productJpa", key = "#id")
    @Transactional(readOnly = true)
    public Optional<Product> findById(UUID id) {
        Objects.requireNonNull(id, "id cannot be null");
        return super.findById(id);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "productJpa", key = "#domain.id"),
    })
    @Transactional
    public Product save(Product domain) {
        return super.save(domain);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "productJpa", key = "#domain.id"),
    })
    @Transactional
    public Product update(Product domain) {
        return super.update(domain);
    }

}
