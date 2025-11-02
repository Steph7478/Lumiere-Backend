package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Auth;
import com.lumiere.infrastructure.jpa.AuthJpaEntity;
import com.lumiere.infrastructure.mappers.base.BaseMapper;

public class AuthMapper extends BaseMapper<Auth, AuthJpaEntity> {

    @Override
    public Auth toDomain(AuthJpaEntity jpaEntity) {
        return Auth.from(
                jpaEntity.getName(),
                jpaEntity.getEmail(),
                jpaEntity.getPassword(),
                jpaEntity.getIsAdmin() != null && jpaEntity.getIsAdmin(),
                jpaEntity.getId());
    }

    @Override
    public AuthJpaEntity toJpa(Auth domain) {
        return new AuthJpaEntity(
                domain.getId(),
                domain.getName(),
                domain.getEmail(),
                domain.getPasswordHash(),
                domain.isAdmin());
    }

    public Auth toDomainSafe(AuthJpaEntity jpaEntity) {
        return Auth.hidden(jpaEntity.getName(), jpaEntity.getEmail());
    }

    public Auth toDomainMe(AuthJpaEntity jpaEntity) {
        return Auth.me(
                jpaEntity.getName(),
                jpaEntity.getEmail(),
                jpaEntity.getIsAdmin() != null && jpaEntity.getIsAdmin());
    }
}
