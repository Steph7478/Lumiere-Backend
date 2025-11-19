package com.lumiere.infrastructure.persistence.jpa.entities;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.lumiere.infrastructure.persistence.jpa.entities.base.BaseJpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "cart")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class CartJpaEntity extends BaseJpaEntity implements Serializable {

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "coupon_code")
    private String coupon;

    @OneToMany(mappedBy = "cart")
    private List<CartJpaEntity> items;

    public CartJpaEntity(UUID id, UUID userId, String coupon) {
        super(id);
        this.userId = userId;
        this.coupon = coupon;
    }
}