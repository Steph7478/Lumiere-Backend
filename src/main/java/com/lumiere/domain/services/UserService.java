package com.lumiere.domain.services;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.factories.UserFactory;

public class UserService {

    private UserService() {
    }

    public static User createUser(Auth auth) {
        User user = UserFactory.from(null, auth);
        auth.setUser(user);
        return user;
    }
}
