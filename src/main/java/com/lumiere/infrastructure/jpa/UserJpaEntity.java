package com.lumiere.infrastructure.jpa;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lumiere.infrastructure.jpa.base.BaseJpaEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Getter
@Entity
public class UserJpaEntity extends BaseJpaEntity {

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "auth_id", nullable = false)
    private AuthJpaEntity auth;

    public UserJpaEntity(UUID id, AuthJpaEntity auth) {
        super(id);
        this.auth = auth;
    }

    protected UserJpaEntity() {
        super(null);
    }
}
