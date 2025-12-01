package com.lumiere.infrastructure.persistence.jpa.repositories.coupon;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.lumiere.domain.entities.Coupon;
import com.lumiere.domain.repositories.CouponRepository;
import com.lumiere.infrastructure.mappers.CouponMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CouponJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.base.BaseRepositoryAdapter;

import jakarta.persistence.EntityManager;

public class CouponJpaRepositoryAdapter extends BaseRepositoryAdapter<Coupon, CouponJpaEntity>
        implements CouponRepository {

    private final CouponJpaRepository jpaRepository;
    private final CouponMapper mapper;

    protected CouponJpaRepositoryAdapter(CouponJpaRepository jpaRepository,
            CouponMapper mapper, EntityManager entityManager) {
        super(jpaRepository, mapper, entityManager, CouponJpaEntity.class);

        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Coupon> findAvailableCoupons(UUID userId, Instant now) {
        return jpaRepository.findAvailableCoupons(userId, now)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

}
