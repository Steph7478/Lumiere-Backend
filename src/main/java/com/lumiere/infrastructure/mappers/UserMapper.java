package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.factories.AuthFactory;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.AuthJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.UserJpaEntity;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Context;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<User, UserJpaEntity> {

    @Mapping(target = "auth", ignore = true)
    UserJpaEntity toJpa(User domain, @Context Object... ctx);

    @Mapping(target = "auth", qualifiedByName = "hiddenAuth")
    User toDomain(UserJpaEntity jpaEntity);

    @Named("hiddenAuth")
    public static Auth mapAuth(AuthJpaEntity jpa) {
        return AuthFactory.from(
                jpa.getName(),
                jpa.getEmail(),
                "**hidden**",
                Boolean.TRUE.equals(jpa.getIsAdmin()),
                UUID.fromString("00000000-0000-0000-0000-000000000000"));
    }
}
