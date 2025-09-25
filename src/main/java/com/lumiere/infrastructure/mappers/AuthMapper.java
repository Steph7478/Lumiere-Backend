package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Auth;
import com.lumiere.infrastructure.jpa.AuthJpaEntity;

public class AuthMapper {

    // JPA → Domain
    public static Auth toDomainFull(AuthJpaEntity jpaEntity) {
        return Auth.from(
                jpaEntity.getId(),
                jpaEntity.getName(),
                jpaEntity.getEmail(),
                jpaEntity.getPassword(),
                jpaEntity.getIsAdmin());
    }

    // mapper safe
    public static Auth toDomainSafe(AuthJpaEntity jpaEntity) {
        return Auth.hidden(
                jpaEntity.getName(),
                jpaEntity.getEmail());
    }

    // auth me
    public static Auth toDomainMe(AuthJpaEntity jpaEntity) {
        return Auth.me(
                jpaEntity.getName(),
                jpaEntity.getEmail(),
                jpaEntity.getId(),
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
