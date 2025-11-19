package com.lumiere.infrastructure.persistence.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "client_order")
public class OrderJpaEntity extends BaseJpaEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity userId;

    @Column(name = "payment_id")
    private UUID paymentId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemJpaEntity> items;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private BigDecimal total;

    public OrderJpaEntity(UUID id, UserJpaEntity userId, Status status, UUID paymentId, BigDecimal total,
            List<OrderItemJpaEntity> items) {
        super(id);
        this.userId = userId;
        this.status = status;
        this.paymentId = paymentId;
        this.total = total;
    }

}
