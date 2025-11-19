package com.lumiere.infrastructure.persistence.jpa.entities;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.lumiere.infrastructure.persistence.jpa.entities.base.BaseJpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "cart")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class CartJpaEntity extends BaseJpaEntity implements Serializable {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity userId;

    @Column
    private String coupon;

    @OneToMany(mappedBy = "cart")
    private List<CartItemJpaEntity> items;

    public CartJpaEntity(UUID id, UserJpaEntity userId, String coupon, List<CartItemJpaEntity> items) {
        super(id);
        this.userId = userId;
        this.coupon = coupon;
        this.items = items;
    }
}