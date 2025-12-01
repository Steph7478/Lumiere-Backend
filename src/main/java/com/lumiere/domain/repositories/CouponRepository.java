package com.lumiere.domain.repositories;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.lumiere.domain.entities.Coupon;
import com.lumiere.domain.repositories.base.BaseRepository;

public interface CouponRepository extends BaseRepository<Coupon> {
    List<Coupon> findAvailableCoupons(UUID userId, Instant now);

}
