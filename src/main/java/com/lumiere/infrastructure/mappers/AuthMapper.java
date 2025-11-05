package com.lumiere.infrastructure.mappers;

import org.springframework.stereotype.Component;

import com.lumiere.domain.entities.Auth;
import com.lumiere.infrastructure.jpa.AuthJpaEntity;
import com.lumiere.infrastructure.mappers.base.BaseMapper;

@Component
public final class AuthMapper implements BaseMapper<Auth, AuthJpaEntity> {

    @Override
    public AuthJpaEntity toJpa(Auth domain) {
        return new AuthJpaEntity(
                domain.getId(),
                domain.getName(),
                domain.getEmail(),
                domain.getPasswordHash(),
                domain.isAdmin());
    }

    @Override
    public Auth toDomain(AuthJpaEntity jpaEntity) {
        return Auth.from(
                jpaEntity.getName(),
                jpaEntity.getEmail(),
                jpaEntity.getPassword(),
                jpaEntity.getIsAdmin() != null && jpaEntity.getIsAdmin(),
                jpaEntity.getId());
    }

    public Auth toDomainMe(AuthJpaEntity jpaEntity) {
        return Auth.from(
                jpaEntity.getName(),
                jpaEntity.getEmail(),
                "***hidden***",
                jpaEntity.getIsAdmin() != null && jpaEntity.getIsAdmin(),
                jpaEntity.getId());
    }
}
