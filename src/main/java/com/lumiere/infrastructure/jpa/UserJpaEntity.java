package com.lumiere.infrastructure.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lumiere.infrastructure.jpa.base.BaseJpaEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserJpaEntity extends BaseJpaEntity {
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "auth_id", nullable = false)
    private AuthJpaEntity auth;
}
