package com.lumiere.infrastructure.persistence.jpa.repositories.product;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, UUID> {

}
