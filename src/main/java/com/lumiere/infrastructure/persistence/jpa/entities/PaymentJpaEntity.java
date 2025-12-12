package com.lumiere.infrastructure.persistence.jpa.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.lumiere.domain.enums.PaymentMethodEnum;
import com.lumiere.infrastructure.persistence.jpa.entities.base.BaseJpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "payment")
public class PaymentJpaEntity extends BaseJpaEntity {

    @Column(nullable = false)
    private PaymentMethodEnum paymentMethod;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    public PaymentJpaEntity(UUID id, PaymentMethodEnum paymentMethod, LocalDateTime paymentDate) {
        super(id);
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
    }
}
