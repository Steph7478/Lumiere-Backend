package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Auth;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.AuthJpaEntity;

import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { UserMapper.class })
public interface AuthMapper extends BaseMapper<Auth, AuthJpaEntity> {

    AuthJpaEntity toJpa(Auth domain);

    @ObjectFactory
    default Auth createAuth(AuthJpaEntity jpa) {
        return Auth.from(
                jpa.getName(),
                jpa.getEmail(),
                jpa.getPassword(),
                jpa.getIsAdmin() != null && jpa.getIsAdmin(),
                jpa.getId());
    }

    @Mapping(target = "admin", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "id", ignore = true)
    Auth toDomain(AuthJpaEntity jpa);
}