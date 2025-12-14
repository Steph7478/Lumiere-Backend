package com.lumiere.infrastructure.persistence.jpa.entities;

import java.time.LocalDateTime;

import com.lumiere.domain.enums.PaymentMethodEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "payment")
public class PaymentJpaEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private PaymentMethodEnum paymentMethod;

    @Column(nullable = false)
    private LocalDateTime paymentDate;
}
