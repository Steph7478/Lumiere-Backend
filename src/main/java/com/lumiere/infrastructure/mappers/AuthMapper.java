package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Auth;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.AuthJpaEntity;

import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper extends BaseMapper<Auth, AuthJpaEntity> {
    AuthJpaEntity toJpa(Auth domain);

    @Mapping(target = "user", ignore = true)

    @Mapping(target = "passwordHash", source = "password")
    Auth toDomain(AuthJpaEntity jpa);
}