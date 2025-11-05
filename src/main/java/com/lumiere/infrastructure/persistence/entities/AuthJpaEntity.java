package com.lumiere.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

import com.lumiere.infrastructure.persistence.entities.base.BaseJpaEntity;

@Getter
@Entity
public class AuthJpaEntity extends BaseJpaEntity {

    @OneToOne(mappedBy = "auth", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private UserJpaEntity user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private Boolean isAdmin;

    public AuthJpaEntity(UUID id, String name, String email, String password, Boolean isAdmin) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    protected AuthJpaEntity() {
        super(null);
    }
}
