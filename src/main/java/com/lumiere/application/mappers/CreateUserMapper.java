package com.lumiere.application.mappers;

import com.lumiere.application.dtos.auth.CreateUserDTO;
import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.services.AuthService;
import com.lumiere.application.mappers.base.BaseMapper;

public class CreateUserMapper implements BaseMapper<Auth, CreateUserDTO> {

    @Override
    public CreateUserDTO toDTO(Auth entity) {
        return new CreateUserDTO(entity.getEmail(), entity.getName(), "**hidden**");
    }

    @Override
    public Auth toEntity(CreateUserDTO dto) {
        return AuthService.createAuth(dto.name(), dto.email(), dto.password(), false);
    }
}
