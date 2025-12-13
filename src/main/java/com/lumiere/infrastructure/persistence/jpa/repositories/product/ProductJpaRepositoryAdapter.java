package com.lumiere.infrastructure.persistence.jpa.repositories.product;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    private final ProductJpaRepository productRepo;
    private final ProductMapper productMapper;

    public ProductJpaRepositoryAdapter(
            ProductJpaRepository productRepo,
            ProductMapper productMapper,
            EntityManager entityManager) {
        super(productRepo, productMapper, entityManager);
        this.productRepo = productRepo;
        this.productMapper = productMapper;
    }

    @Override
    @Cacheable(value = "productJpa", key = "#id", unless = "#result == null")
    @Transactional(readOnly = true)
    public Optional<Product> findById(UUID id) {
        Objects.requireNonNull(id, "id cannot be null");
        return super.findById(id);
    }

    @Override
    @CacheEvict(value = "productJpa", key = "#domain.id")
    @Transactional
    public Product save(Product domain) {
        return super.save(domain);
    }

    @Override
    @CacheEvict(value = "productJpa", key = "#domain.id")
    @Transactional
    public Product update(Product domain) {
        return super.update(domain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAllByIdIn(Collection<UUID> ids) {
        Objects.requireNonNull(ids, "ids cannot be null");

        List<ProductJpaEntity> jpaEntities = productRepo.findAllByIdIn(ids);

        return jpaEntities.stream()
                .map(productMapper::toDomain)
                .collect(Collectors.toList());
    }
}
