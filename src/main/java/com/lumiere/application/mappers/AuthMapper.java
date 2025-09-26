package com.lumiere.application.mappers;

import com.lumiere.application.dtos.auth.CreateUserDTO;
import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.services.AuthService;

public class AuthMapper {

    // DTO -> entity
    public static Auth toEntity(CreateUserDTO dto) {
        return AuthService.createAuth(
                dto.name(),
                dto.email(),
                dto.password(),
                false);
    }

    // entity -> DTO
    public static CreateUserDTO toDTO(Auth user) {
        return new CreateUserDTO(
                user.getEmail(),
                user.getName(),
                "**hidden**");
    }
}
