package com.lumiere.infrastructure.persistence.jpa.entities;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.lumiere.infrastructure.persistence.jpa.entities.base.BaseJpaEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CartJpaEntity
        extends BaseJpaEntity implements Serializable {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemJpaEntity> items;

    public CartJpaEntity(UUID id, UserJpaEntity user) {
        super(id);
        this.user = user;
    }
}