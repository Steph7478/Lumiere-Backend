package com.lumiere.presentation.dtos.auth.updateUser;

import java.util.Optional;

public record UpdateUserRequest(
        Optional<String> name,
        Optional<String> email,
        Optional<String> newPassword) {

}
