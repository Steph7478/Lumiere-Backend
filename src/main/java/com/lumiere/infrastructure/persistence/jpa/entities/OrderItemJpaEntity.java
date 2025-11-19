package com.lumiere.infrastructure.persistence.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import com.lumiere.infrastructure.persistence.jpa.entities.base.BaseJpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "order_item")
public class OrderItemJpaEntity extends BaseJpaEntity implements Serializable {

    @ManyToOne
    @Column(name = "product_id", nullable = false)
    private ProductJpaEntity productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    public OrderItemJpaEntity(UUID id, ProductJpaEntity productId, String name, int quantity, BigDecimal unitPrice) {
        super(id);
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
