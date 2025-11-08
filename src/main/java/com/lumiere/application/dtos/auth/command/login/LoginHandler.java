package com.lumiere.application.dtos.auth.command.login;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record LoginHandler(@JsonIgnore String accessToken, @JsonIgnore String refreshToken, String name, String role) {
}
