package com.lumiere.infrastructure.persistence.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import com.lumiere.domain.enums.CategoriesEnum.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "order_item")
public class OrderItemJpaEntity implements Serializable {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductJpaEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderJpaEntity order;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private Category category;

    public OrderItemJpaEntity(UUID id, OrderJpaEntity order, ProductJpaEntity product, String name, int quantity,
            BigDecimal unitPrice, String currency, Category category) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.category = category;
    }

    protected void setOrderReference(OrderJpaEntity order) {
        this.order = order;
    }
}
