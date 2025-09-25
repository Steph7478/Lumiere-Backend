package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Auth;
import com.lumiere.infrastructure.jpa.AuthJpaEntity;

public class AuthMapper {

    // JPA → Domain
    public static Auth toDomain(AuthJpaEntity jpaEntity) {
        return Auth.from(
                jpaEntity.getId(),
                jpaEntity.getName(),
                jpaEntity.getEmail(),
                "***hidden***",
                jpaEntity.getIsAdmin());
    }

    // Domain → JPA
    public static AuthJpaEntity toJpa(Auth domain) {
        AuthJpaEntity jpa = new AuthJpaEntity();
        jpa.setId(domain.getId());
        jpa.setName(domain.getName());
        jpa.setEmail(domain.getEmail());
        jpa.setPassword(domain.getPasswordHash());
        jpa.setisAdmin(domain.getIsAdmin());
        return jpa;
    }
}
