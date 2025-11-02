package com.lumiere.infrastructure.mappers.base;

public abstract class BaseMapper<Domain, Jpa> {

    public abstract Domain toDomain(Jpa jpaEntity);

    public abstract Jpa toJpa(Domain domain);
}
