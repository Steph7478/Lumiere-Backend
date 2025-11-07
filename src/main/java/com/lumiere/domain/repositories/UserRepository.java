package com.lumiere.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import com.lumiere.domain.entities.User;
import com.lumiere.domain.readmodels.AuthInfoView;
import com.lumiere.domain.readmodels.UserInfoView;
import com.lumiere.domain.repositories.base.BaseRepository;

public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByAuthEmail(String email);

    Optional<UserInfoView> findUserInfoById(UUID id);

    Optional<AuthInfoView> findAuthInfoByAuthId(UUID id);

}
