package com.lumiere.infrastructure.mappers;

import org.springframework.stereotype.Component;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.entities.User;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.entities.AuthJpaEntity;
import com.lumiere.infrastructure.persistence.entities.UserJpaEntity;

@Component
public class UserMapper implements BaseMapper<User, UserJpaEntity> {

    @Override
    public User toDomain(UserJpaEntity jpaEntity) {
        if (jpaEntity == null)
            return null;

        AuthJpaEntity authJpa = jpaEntity.getAuth();
        Auth auth = Auth.from(
                authJpa.getName(),
                authJpa.getEmail(),
                "***hidden***",
                false,
                null);

        return User.from(jpaEntity.getId(), auth);
    }

    @Override
    public UserJpaEntity toJpa(User domain) {
        if (domain == null)
            return null;

        AuthJpaEntity auth = new AuthJpaEntity(
                domain.getAuth().getId(),
                domain.getAuth().getName(),
                domain.getAuth().getEmail(),
                domain.getAuth().getPasswordHash(),
                domain.getAuth().isAdmin());

        return new UserJpaEntity(domain.getId(), auth);
    }
}
