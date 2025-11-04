package com.lumiere.infrastructure.repositories.User;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.lumiere.domain.entities.User;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.infrastructure.jpa.AuthJpaEntity;
import com.lumiere.infrastructure.jpa.UserJpaEntity;
import com.lumiere.infrastructure.mappers.AuthMapper;
import com.lumiere.infrastructure.mappers.UserMapper;
import com.lumiere.infrastructure.repositories.Auth.AuthJpaRepository;

@Repository
public class UserJpaRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userRepo;
    private final AuthJpaRepository authRepo;
    private final AuthMapper authMapper;
    private final UserMapper userMapper;

    public UserJpaRepositoryAdapter(UserJpaRepository userRepo, AuthJpaRepository authRepo,
            AuthMapper authMapper, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.authRepo = authRepo;
        this.authMapper = authMapper;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        AuthJpaEntity authEntity = authMapper.toJpa(user.getAuth());
        Objects.requireNonNull(authEntity, "authEntity cannot be null");
        authRepo.save(authEntity);

        UserJpaEntity userJpa = userMapper.toJpa(user);
        Objects.requireNonNull(userJpa, "userJpa cannot be null");
        userJpa = userRepo.save(userJpa);

        return userMapper.toDomain(userJpa);
    }

    @Override
    public Optional<User> findById(UUID id) {
        Objects.requireNonNull(id, "id cannot be null");
        return userRepo.findById(id)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByAuthEmail(String email) {
        Objects.requireNonNull(email, "email cannot be null");
        return userRepo.findByAuthEmail(email)
                .map(userMapper::toDomain);
    }
}