package com.lumiere.application.mappers;

import org.springframework.stereotype.Component;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.services.AuthService;
import com.lumiere.application.dtos.auth.command.create.CreateUserDTO;
import com.lumiere.application.mappers.base.BaseMapper;

@Component
public class CreateUserUseCaseMapper implements BaseMapper<Auth, CreateUserDTO> {

    @Override
    public CreateUserDTO toDTO(Auth entity) {
        return new CreateUserDTO(entity.getEmail(), entity.getName(), "**hidden**");
    }

    @Override
    public Auth toEntity(CreateUserDTO dto) {
        return AuthService.createAuth(dto.name(), dto.email(), dto.password(), false);
    }
}
