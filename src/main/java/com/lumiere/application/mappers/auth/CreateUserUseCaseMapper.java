package com.lumiere.application.mappers.auth;

import org.springframework.stereotype.Component;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.services.AuthService;
import com.lumiere.application.dtos.auth.command.create.CreateUserInput;
import com.lumiere.application.mappers.base.BaseMapper;

@Component
public class CreateUserUseCaseMapper implements BaseMapper<Auth, CreateUserInput> {

    @Override
    public CreateUserInput toDTO(Auth entity) {
        return new CreateUserInput(entity.getEmail(), entity.getName(), "**hidden**");
    }

    @Override
    public Auth toEntity(CreateUserInput dto) {
        return AuthService.createAuth(dto.name(), dto.email(), dto.password(), false);
    }
}
