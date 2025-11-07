package com.lumiere.infrastructure.mappers;

import org.springframework.stereotype.Component;
import java.util.Optional;

import com.lumiere.domain.entities.Auth;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.entities.AuthJpaEntity;

@Component
public final class AuthMapper implements BaseMapper<Auth, AuthJpaEntity> {

    private final UserMapper userMapper;

    public AuthMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
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

    @Override
    public Auth toDomain(AuthJpaEntity jpaEntity) {
        Auth auth = Auth.from(
                jpaEntity.getName(),
                jpaEntity.getEmail(),
                jpaEntity.getPassword(),
                jpaEntity.getIsAdmin() != null && jpaEntity.getIsAdmin(),
                jpaEntity.getId());

        Optional.ofNullable(jpaEntity.getUser())
                .map(userMapper::toDomain)
                .ifPresent(auth::setUser);

        return auth;
    }

}