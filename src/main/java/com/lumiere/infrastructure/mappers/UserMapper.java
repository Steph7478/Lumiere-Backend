package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.entities.User;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.AuthJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.entities.UserJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserJpaEntity> {

    @Mapping(target = "auth", source = "auth")
    UserJpaEntity toJpa(User domain);

    @Mapping(target = "auth", source = "auth")
    User toDomain(UserJpaEntity jpaEntity);

    @ObjectFactory
    default User createUser(UserJpaEntity jpaEntity, @TargetType Class<User> targetType) {
        if (jpaEntity == null)
            return null;

        Auth auth = mapAuthJpaEntityToAuth(jpaEntity.getAuth());
        return User.from(jpaEntity.getId(), auth);
    }

    default AuthJpaEntity mapAuthToAuthJpaEntity(Auth auth) {
        return new AuthJpaEntity(
                auth.getId(),
                auth.getName(),
                auth.getEmail(),
                auth.getPasswordHash(),
                auth.isAdmin());
    }

    default Auth mapAuthJpaEntityToAuth(AuthJpaEntity jpaEntity) {
        return Auth.from(
                jpaEntity.getName(),
                jpaEntity.getEmail(),
                "***hidden***",
                jpaEntity.getIsAdmin() != null && jpaEntity.getIsAdmin(),
                jpaEntity.getId());
    }
}