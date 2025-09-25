package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.entities.User;
import com.lumiere.infrastructure.jpa.AuthJpaEntity;
import com.lumiere.infrastructure.jpa.UserJpaEntity;

public class UserMapper {

    // JPA -> Domain
    public static User toDomain(UserJpaEntity jpaEntity) {
        Auth auth = Auth.hidden(jpaEntity.getName(), jpaEntity.getEmail());

        return User.from(jpaEntity.getId(), auth);
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
