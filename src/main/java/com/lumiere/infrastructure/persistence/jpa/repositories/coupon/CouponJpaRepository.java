package com.lumiere.infrastructure.persistence.jpa.repositories.coupon;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lumiere.infrastructure.persistence.jpa.entities.CouponJpaEntity;

public interface CouponJpaRepository extends JpaRepository<CouponJpaEntity, UUID> {

    @Query(value = "SELECT DISTINCT c FROM CouponJpaEntity c LEFT JOIN FETCH c.user " +
            "WHERE (c.user.id = :userId OR c.user IS NULL) " +
            "AND c.expiredAt > :now")
    List<CouponJpaEntity> findAvailableCoupons(UUID userId, Instant now);
}
