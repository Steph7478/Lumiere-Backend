package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.entities.User;
import com.lumiere.infrastructure.jpa.AuthJpaEntity;
import com.lumiere.infrastructure.jpa.UserJpaEntity;

public class UserMapper {

    // JPA -> Domain
    public static User toDomain(UserJpaEntity jpaEntity) {
        if (jpaEntity == null)
            return null;

        AuthJpaEntity authJpa = jpaEntity.getAuth();
        Auth auth = Auth.hidden(authJpa.getName(), authJpa.getEmail());

        return User.from(jpaEntity.getId(), auth);
    }

    // Domain -> JPA
    public static UserJpaEntity toJpa(User domain, AuthJpaEntity existingAuth) {
        if (domain == null)
            return null;

        UserJpaEntity jpa = new UserJpaEntity();
        jpa.setId(domain.getId());

        if (existingAuth != null) {
            jpa.setAuth(existingAuth);
        } else {
            AuthJpaEntity auth = new AuthJpaEntity();
            auth.setName(domain.getAuth().getName());
            auth.setEmail(domain.getAuth().getEmail());
            jpa.setAuth(auth);
        }

        return jpa;
    }
}
