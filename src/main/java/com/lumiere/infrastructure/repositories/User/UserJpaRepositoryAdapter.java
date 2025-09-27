package com.lumiere.infrastructure.repositories.User;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.lumiere.domain.entities.Auth;
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

    public UserJpaRepositoryAdapter(UserJpaRepository userRepo, AuthJpaRepository authRepo) {
        this.userRepo = userRepo;
        this.authRepo = authRepo;
    }

    @Override
    public User save(User user) {
        Auth authDomain = user.getAuth();
        AuthJpaEntity authJpa = AuthMapper.toJpa(authDomain);
        authJpa = authRepo.save(authJpa);

        UserJpaEntity userJpa = UserMapper.toJpa(user, authJpa);
        userJpa = userRepo.save(userJpa);

        return UserMapper.toDomain(userJpa);
    }

    @Override
    public User findById(UUID id) {
        return userRepo.findById(id)
                .map(UserMapper::toDomain)
                .orElse(null);
    }

    @Override
    public User findByAuthEmail(String email) {
        return userRepo.findByAuthEmail(email)
                .map(UserMapper::toDomain)
                .orElse(null);
    }

}
