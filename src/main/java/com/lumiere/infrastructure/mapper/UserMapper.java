package com.lumiere.infrastructure.mapper;

import com.lumiere.domain.entity.Auth;
import com.lumiere.domain.entity.User;
import com.lumiere.infrastructure.jpa.AuthJpaEntity;
import com.lumiere.infrastructure.jpa.UserJpaEntity;

public class UserMapper {

    // JPA -> Domain
    public static User toDomain(UserJpaEntity jpaEntity) {
        Auth auth = new Auth(
                null,
                jpaEntity.getName(),
                jpaEntity.getEmail(),
                null,
                false);

        return new User(jpaEntity.getId(), auth);
    }

    // Domain -> JPA
    public static UserJpaEntity toJpa(User domain) {
        UserJpaEntity jpa = new UserJpaEntity();
        jpa.setId(domain.getId());

        AuthJpaEntity auth = new AuthJpaEntity();
        auth.setName(domain.getName());
        auth.setEmail(domain.getEmail());
        jpa.setAuth(auth);

        return jpa;
    }
}
