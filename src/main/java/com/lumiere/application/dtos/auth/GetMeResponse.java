package com.lumiere.application.dtos.auth;

public record GetMeResponse(
        String name,
        boolean isAdmin) {

}
