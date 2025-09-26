package com.lumiere.infrastructure.repositories.User;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.lumiere.domain.entities.User;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.infrastructure.jpa.UserJpaEntity;
import com.lumiere.infrastructure.mappers.UserMapper;

@Repository
public class UserJpaRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userRepo;

    public UserJpaRepositoryAdapter(UserJpaRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User save(User user) {
        UserJpaEntity jpa = UserMapper.toJpa(user);
        jpa = userRepo.save(jpa);
        return UserMapper.toDomain(jpa);
    }

    @Override
    public User findUserById(UUID id) {
        return userRepo.findUserById(id)
                .map(UserMapper::toDomain)
                .orElse(null);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findUserByEmail(email)
                .map(UserMapper::toDomain)
                .orElse(null);
    }

}
