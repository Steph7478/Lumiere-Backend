package com.lumiere.infrastructure.persistence.jpa.repositories.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.repositories.ProductRepository;
import com.lumiere.infrastructure.mappers.ProductMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.base.BaseRepositoryAdapter;
import com.lumiere.shared.annotations.validators.validEntityGraphPath.ValidEntityGraphPaths;

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
    public Optional<Product> findByIdWithRelations(UUID id,
            @ValidEntityGraphPaths(root = ProductJpaEntity.class, allowedPaths = {}) String... relations) {
        return findByIdWithEager(id, relations);
    }

    @Override
    public List<User> findAllWithRelations() {
        throw new UnsupportedOperationException("Unimplemented method 'findAllWithRelations'");
    }

}
