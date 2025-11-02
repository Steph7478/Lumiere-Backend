package com.lumiere.presentation.mappers.base;

public interface BaseResponseMapper<Application, Presentation> {
    Presentation toPresentationDTO(Application responseDTO);
}
