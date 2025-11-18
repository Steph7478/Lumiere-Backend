package com.lumiere.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import com.lumiere.infrastructure.persistence.jpa.entities.base.BaseJpaEntity;

@Getter
@Entity
@Table(name = "products")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class ProductJpaEntity extends BaseJpaEntity implements Serializable {

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
            Integer stockQuantity) {
        super(id);
        this.name = name;
        this.description = description;
        this.priceAmount = priceAmount;
        this.priceCurrency = priceCurrency;
        this.stockQuantity = stockQuantity;
    }
}