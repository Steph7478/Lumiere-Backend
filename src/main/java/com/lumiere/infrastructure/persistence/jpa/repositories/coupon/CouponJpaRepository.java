package com.lumiere.infrastructure.persistence.jpa.repositories.coupon;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lumiere.infrastructure.persistence.jpa.entities.CouponJpaEntity;

public interface CouponJpaRepository extends JpaRepository<CouponJpaEntity, UUID> {

}
