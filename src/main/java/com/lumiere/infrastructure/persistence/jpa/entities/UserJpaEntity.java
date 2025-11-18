package com.lumiere.infrastructure.persistence.jpa.entities;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lumiere.infrastructure.persistence.jpa.entities.base.BaseJpaEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class UserJpaEntity extends BaseJpaEntity implements Serializable {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_id", nullable = false)
    @JsonIgnore
    private AuthJpaEntity auth;

    public UserJpaEntity(UUID id, AuthJpaEntity auth) {
        super(id);
        this.auth = auth;
    }
}
