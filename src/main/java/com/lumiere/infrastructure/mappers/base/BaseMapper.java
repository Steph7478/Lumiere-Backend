package com.lumiere.infrastructure.mappers.base;

public interface BaseMapper<Domain, Jpa> {
    Domain toDomain(Jpa jpaEntity);

    Jpa toJpa(Domain domain);
}
