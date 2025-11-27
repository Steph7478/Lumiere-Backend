package com.lumiere.application.mappers.auth;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ReportingPolicy;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.services.AuthService;
import com.lumiere.application.dtos.auth.command.create.CreateUserInput;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateUserUseCaseMapper {

    @Mapping(target = "email", source = "email")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "password", expression = "java(\"**hidden**\")")
    CreateUserInput toDTO(Auth entity);

    Auth toEntity(CreateUserInput dto);

    @ObjectFactory
    default Auth createUser(CreateUserInput dto) {
        return AuthService.createAuth(dto.name(), dto.email(), dto.password(), false);
    }
}