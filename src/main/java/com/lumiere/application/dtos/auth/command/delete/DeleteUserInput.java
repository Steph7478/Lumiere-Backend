package com.lumiere.application.dtos.auth.command.delete;
import jakarta.validation.constraints.*;
import java.util.UUID;

public record DeleteUserInput(@NotNull UUID id) {

}
