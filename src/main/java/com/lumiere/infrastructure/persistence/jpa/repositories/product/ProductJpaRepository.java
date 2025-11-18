package com.lumiere.infrastructure.persistence.jpa.repositories.product;

import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, UUID> {

        @Query(value = """
                        SELECT DISTINCT p FROM ProductJpaEntity p
                        LEFT JOIN FETCH p.ratings
                        WHERE (:#{#productIds.size()} = 0 OR p.id IN :productIds)
                        AND (:name IS NULL OR lower(p.name) LIKE lower(concat('%', :name, '%')))
                        AND (:priceMin IS NULL OR p.priceAmount >= :priceMin)
                        AND (:priceMax IS NULL OR p.priceAmount <= :priceMax)
                        """, countQuery = """
                        SELECT count(p) FROM ProductJpaEntity p
                        WHERE (:#{#productIds.size()} = 0 OR p.id IN :productIds)
                        AND (:name IS NULL OR lower(p.name) LIKE lower(concat('%', :name, '%')))
                        AND (:priceMin IS NULL OR p.priceAmount >= :priceMin)
                        AND (:priceMax IS NULL OR p.priceAmount <= :priceMax)
                        """)
        Page<ProductJpaEntity> findFilteredProducts(
                        @Param("productIds") List<UUID> productIds,
                        @Param("name") String name,
                        @Param("priceMin") BigDecimal priceMin,
                        @Param("priceMax") BigDecimal priceMax,
                        Pageable pageable);

        @NonNull
        @EntityGraph(attributePaths = "ratings")
        Optional<ProductJpaEntity> findById(@Nullable UUID id);
}