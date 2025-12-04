package com.lumiere.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.lumiere.infrastructure.persistence.jpa.entities.base.BaseJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_product_price", columnList = "priceAmount"),
        @Index(name = "idx_product_name", columnList = "name")
})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ProductJpaEntity extends BaseJpaEntity implements Serializable {

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private BigDecimal priceAmount;

    @Column(nullable = false)
    private String priceCurrency;

    @Column(nullable = false)
    private Integer stockQuantity;

    @OneToMany(mappedBy = "productId", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RatingJpaEntity> ratings;

    public ProductJpaEntity(
            UUID id,
            String name,
            String description,
            BigDecimal priceAmount,
            String priceCurrency,
            Integer stockQuantity, String imageUrl) {
        super(id);
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.priceAmount = priceAmount;
        this.priceCurrency = priceCurrency;
        this.stockQuantity = stockQuantity;
    }

    public ProductJpaEntity(UUID id) {
        super(id);
    }

}