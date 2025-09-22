package com.lumiere.infrastructure.mapper;

import com.lumiere.domain.entity.User;
import com.lumiere.infrastructure.jpa.AuthJpaEntity;
import com.lumiere.infrastructure.jpa.UserJpaEntity;

public class UserMapper {

    // JPA -> Domain
    public static User toDomain(UserJpaEntity jpaEntity) {
        return new User(
                jpaEntity.getId(),
                null,
                jpaEntity.getName(),
                jpaEntity.getEmail());
    }

    // Domain -> JPA
    public static UserJpaEntity toJpa(User domain) {
        UserJpaEntity jpa = new UserJpaEntity();
        jpa.setId(domain.getId());

        if (domain.getAuthId() != null) {
            AuthJpaEntity auth = new AuthJpaEntity();
            auth.setId(domain.getAuthId());
            jpa.setAuth(auth);
        }

        return jpa;
    }
}
