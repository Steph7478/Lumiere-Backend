package com.lumiere.infrastructure.persistence.jpa.repositories.product;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;

@Repository
public interface ProductJpaRepository
                extends JpaRepository<ProductJpaEntity, UUID>,
                JpaSpecificationExecutor<ProductJpaEntity> {

        @EntityGraph(attributePaths = { "ratings" })
        @NonNull
        Page<ProductJpaEntity> findAll(
                        @Nullable Specification<ProductJpaEntity> spec,
                        @Nullable Pageable pageable);
}