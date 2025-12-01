package com.lumiere.infrastructure.persistence.jpa.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.enums.CouponEnum.Type;
import com.lumiere.infrastructure.persistence.jpa.entities.base.BaseJpaEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CouponJpaEntity extends BaseJpaEntity {

    private LocalDateTime expiredAt;
    private Category category;
    private BigDecimal discount;
    private Type type;
    private boolean isUnique;
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    public CouponJpaEntity(UUID id, LocalDateTime expiredAt, Category category, BigDecimal discount, Type type,
            boolean isUnique,
            String code) {
        super(id);
        this.expiredAt = expiredAt;
        this.category = category;
        this.discount = discount;
        this.type = type;
        this.isUnique = isUnique;
        this.code = code;
    }
}
