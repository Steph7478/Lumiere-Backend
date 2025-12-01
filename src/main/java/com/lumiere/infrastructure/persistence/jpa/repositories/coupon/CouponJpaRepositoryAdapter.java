package com.lumiere.infrastructure.persistence.jpa.repositories.coupon;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lumiere.domain.entities.Coupon;
import com.lumiere.domain.repositories.CouponRepository;
import com.lumiere.infrastructure.mappers.CouponMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CouponJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.base.BaseRepositoryAdapter;

import jakarta.persistence.EntityManager;

public class CouponJpaRepositoryAdapter extends BaseRepositoryAdapter<Coupon, CouponJpaEntity>
        implements CouponRepository {

    protected CouponJpaRepositoryAdapter(JpaRepository<CouponJpaEntity, UUID> jpaRepository,
            CouponMapper mapper, EntityManager entityManager) {
        super(jpaRepository, mapper, entityManager, CouponJpaEntity.class);
    }

}
