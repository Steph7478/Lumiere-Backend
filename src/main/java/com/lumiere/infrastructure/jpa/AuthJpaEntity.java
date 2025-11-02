package com.lumiere.infrastructure.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import com.lumiere.infrastructure.jpa.base.BaseJpaEntity;

@Getter
@Setter
@Entity
public class AuthJpaEntity extends BaseJpaEntity {

    @OneToOne(mappedBy = "auth")
    private UserJpaEntity user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private Boolean isAdmin;
}
