package com.lumiere.infrastructure.persistence.jpa.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.lumiere.infrastructure.persistence.jpa.entities.base.BaseJpaEntity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CartJpaEntity extends BaseJpaEntity implements Serializable {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity user;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemJpaEntity> items = new ArrayList<>();

    public void setItems(List<CartItemJpaEntity> items) {
        this.items.clear();
        if (items != null) {
            this.items.addAll(items);
            for (CartItemJpaEntity item : this.items) {
                item.setCartReference(this);
            }
        }
    }

    public CartJpaEntity(UUID id, UserJpaEntity user) {
        super(id);
        this.user = user;
        this.setItems(items);
    }

}