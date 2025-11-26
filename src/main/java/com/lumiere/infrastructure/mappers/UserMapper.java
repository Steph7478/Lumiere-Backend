package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.factories.AuthFactory;
import com.lumiere.domain.factories.UserFactory;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.AuthJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.UserJpaEntity;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.TargetType;
import org.mapstruct.Context;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<User, UserJpaEntity> {

    UserJpaEntity toJpa(User domain, @Context Object... ctx);

    @Override
    @Mapping(target = "auth", ignore = true)
    User toDomain(UserJpaEntity jpaEntity);

    @ObjectFactory
    default User createUser(UserJpaEntity jpaEntity, @TargetType Class<User> targetType) {
        if (jpaEntity == null)
            return null;

        Auth auth = mapAuthJpaEntityToAuth(jpaEntity.getAuth());
        return UserFactory.from(jpaEntity.getId(), auth);
    }

    default Auth mapAuthJpaEntityToAuth(AuthJpaEntity jpaEntity) {
        if (jpaEntity == null)
            return null;

        return AuthFactory.from(
                jpaEntity.getName(),
                jpaEntity.getEmail(),
                "**hidden**",
                Boolean.TRUE.equals(jpaEntity.getIsAdmin()),
                UUID.fromString("00000000-0000-0000-0000-000000000000"));
    }
}
