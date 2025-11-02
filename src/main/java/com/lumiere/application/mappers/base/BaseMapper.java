package com.lumiere.application.mappers.base;

public interface BaseMapper<E, D> {
    D toDTO(E entity);

    E toEntity(D dto);
}
