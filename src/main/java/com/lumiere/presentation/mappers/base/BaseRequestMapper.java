package com.lumiere.presentation.mappers.base;

public interface BaseRequestMapper<Presentation, Application> {
    Application toApplicationDTO(Presentation requestDTO);
}
