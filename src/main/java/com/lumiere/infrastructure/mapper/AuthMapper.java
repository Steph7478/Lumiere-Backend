package com.lumiere.infrastructure.mapper;

import com.lumiere.domain.entity.Auth;
import com.lumiere.infrastructure.jpa.AuthJpaEntity;

public class AuthMapper {

    // JPA → Domain
    public static Auth toDomain(AuthJpaEntity jpaEntity) {
        return Auth.from(
                jpaEntity.getId(),
                jpaEntity.getName(),
                jpaEntity.getEmail(),
                null,
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
