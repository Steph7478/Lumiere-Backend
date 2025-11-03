package com.lumiere.infrastructure.repositories.User;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.lumiere.domain.entities.User;
import com.lumiere.domain.repositories.UserRepository;
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

    public UserJpaRepositoryAdapter(UserJpaRepository userRepo, AuthJpaRepository authRepo, AuthMapper authMapper,
            UserMapper userMapper) {
        this.userRepo = userRepo;
        this.authRepo = authRepo;
        this.authMapper = authMapper;
        this.userMapper = userMapper;

    }

    @Override
    public User save(User user) {
        if (user == null)
            return null;

        authRepo.save(authMapper.toJpa(user.getAuth()));

        UserJpaEntity userJpa = userMapper.toJpa(user);
        userJpa = userRepo.save(userJpa);

        return userMapper.toDomain(userJpa);
    }

    @Override
    public User findById(UUID id) {
        return userRepo.findById(id)
                .map(userMapper::toDomain)
                .orElse(null);
    }

    @Override
    public User findByAuthEmail(String email) {
        return userRepo.findByAuthEmail(email)
                .map(userMapper::toDomain)
                .orElse(null);
    }

}
