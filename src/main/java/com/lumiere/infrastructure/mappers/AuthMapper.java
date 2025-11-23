package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.factories.AuthFactory;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.AuthJpaEntity;

import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper extends BaseMapper<Auth, AuthJpaEntity> {
    AuthJpaEntity toJpa(Auth domain);

    @Mapping(target = "user", ignore = true)
    Auth toDomain(AuthJpaEntity jpa);

    @ObjectFactory
    default Auth createAuth(AuthJpaEntity jpa) {
        return AuthFactory.from(
                jpa.getName(),
                jpa.getEmail(),
                jpa.getPassword(),
                jpa.getIsAdmin() != null && jpa.getIsAdmin(),
                jpa.getId());
    }

}