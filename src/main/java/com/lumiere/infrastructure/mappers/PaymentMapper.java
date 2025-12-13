package com.lumiere.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.lumiere.domain.entities.Payment;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.PaymentJpaEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper extends BaseMapper<Payment, PaymentJpaEntity> {
    Payment toDomain(PaymentJpaEntity jpaEntity);

    PaymentJpaEntity toJpa(Payment domain);
}
