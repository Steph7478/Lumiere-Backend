package com.lumiere.infrastructure.persistence.jpa.repositories.coupon;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lumiere.infrastructure.persistence.jpa.entities.CouponJpaEntity;

@Repository
public interface CouponJpaRepository extends JpaRepository<CouponJpaEntity, UUID> {

    @Query(value = "SELECT DISTINCT c FROM CouponJpaEntity c LEFT JOIN FETCH c.user " +
            "WHERE (c.user.id = :userId OR c.user IS NULL) " +
            "AND c.expiredAt > :now")
    @EntityGraph(attributePaths = { "user" })
    List<CouponJpaEntity> findAvailableCoupons(UUID userId, LocalDateTime now);
}
