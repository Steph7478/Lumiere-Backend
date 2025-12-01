package com.lumiere.infrastructure.persistence.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.lumiere.domain.enums.StatusEnum.Status;
import com.lumiere.infrastructure.persistence.jpa.entities.base.BaseJpaEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "client_order")
public class OrderJpaEntity extends BaseJpaEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity user;

    @Column(name = "payment_id")
    private UUID paymentId;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemJpaEntity> items = new ArrayList<>();

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(name = "coupon")
    private String coupon;

    @Column(nullable = false)
    private String currency;

    public void setItems(List<OrderItemJpaEntity> items) {
        this.items.clear();
        if (items != null) {
            this.items.addAll(items);
            for (OrderItemJpaEntity item : this.items) {
                item.setOrderReference(this);
            }
        }
    }

    public OrderJpaEntity(UUID id, UserJpaEntity user, Status status, UUID paymentId, BigDecimal total,
            List<OrderItemJpaEntity> items, String coupon) {
        super(id);
        this.user = user;
        this.status = status;
        this.paymentId = paymentId;
        this.total = total;
        this.coupon = coupon;
        this.setItems(items);
    }
}