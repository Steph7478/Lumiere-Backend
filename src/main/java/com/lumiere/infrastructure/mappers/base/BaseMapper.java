package com.lumiere.infrastructure.mappers.base;

import org.mapstruct.Context;

public interface BaseMapper<Domain, Jpa> {
    Domain toDomain(Jpa jpaEntity);

    Jpa toJpa(Domain domain, @Context Object... context);
}
