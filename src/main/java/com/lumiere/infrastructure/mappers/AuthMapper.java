package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Auth;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.AuthJpaEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper extends BaseMapper<Auth, AuthJpaEntity> {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "withPasswordHash", ignore = true)
    Auth toDomain(AuthJpaEntity jpa);

    @Mapping(target = "password", source = "passwordHash")
    @Mapping(target = "isAdmin", source = "admin")
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
}