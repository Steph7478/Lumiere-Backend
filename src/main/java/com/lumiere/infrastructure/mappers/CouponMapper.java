package com.lumiere.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.lumiere.domain.entities.Coupon;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.CouponJpaEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CouponMapper extends BaseMapper<Coupon, CouponJpaEntity> {
    CouponJpaEntity toJpa(Coupon domain);

    Coupon toDomain(CouponJpaEntity jpaEntity);
}
