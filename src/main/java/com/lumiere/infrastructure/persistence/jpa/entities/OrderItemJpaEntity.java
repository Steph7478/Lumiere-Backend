package com.lumiere.infrastructure.persistence.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "order_item")
public class OrderItemJpaEntity implements Serializable {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductJpaEntity product;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderJpaEntity order;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    public OrderItemJpaEntity(UUID id, OrderJpaEntity order, ProductJpaEntity product, String name, int quantity,
            BigDecimal unitPrice) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    protected void setOrderReference(OrderJpaEntity order) {
        this.order = order;
    }
}
