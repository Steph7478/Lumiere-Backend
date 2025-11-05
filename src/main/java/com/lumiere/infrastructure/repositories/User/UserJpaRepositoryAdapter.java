package com.lumiere.infrastructure.repositories.User;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lumiere.domain.entities.User;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.infrastructure.jpa.AuthJpaEntity;
import com.lumiere.infrastructure.jpa.UserJpaEntity;
import com.lumiere.infrastructure.mappers.AuthMapper;
import com.lumiere.infrastructure.mappers.UserMapper;
import com.lumiere.infrastructure.repositories.Auth.AuthJpaRepository;
import com.lumiere.infrastructure.repositories.base.BaseRepositoryAdapter;

@Repository
public class UserJpaRepositoryAdapter extends BaseRepositoryAdapter<User, UserJpaEntity>
        implements UserRepository {

    private final AuthJpaRepository authRepo;
    private final AuthMapper authMapper;

    public UserJpaRepositoryAdapter(
            UserJpaRepository userRepo,
            AuthJpaRepository authRepo,
            AuthMapper authMapper,
            UserMapper userMapper) {

        super(userRepo, userMapper);
        this.authRepo = authRepo;
        this.authMapper = authMapper;
    }

    @Override
    public User save(User user) {
        AuthJpaEntity authEntity = authMapper.toJpa(user.getAuth());
        Objects.requireNonNull(authEntity, "auth entity cannot be null");
        AuthJpaEntity savedAuth = authRepo.save(authEntity);
        user.setAuth(authMapper.toDomain(savedAuth));
        return super.save(user);
    }

    @Override
    public Optional<User> findByAuthEmail(String email) {
        return ((UserJpaRepository) jpaRepository)
                .findByAuthEmail(email)
                .map(((UserMapper) mapper)::toDomain);
    }
}
