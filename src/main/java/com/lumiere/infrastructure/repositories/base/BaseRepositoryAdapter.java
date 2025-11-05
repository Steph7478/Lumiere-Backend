package com.lumiere.infrastructure.repositories.base;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lumiere.infrastructure.mappers.base.BaseMapper;

public abstract class BaseRepositoryAdapter<D, E> implements BaseReader<D>, BaseWriter<D> {

    protected final JpaRepository<E, UUID> jpaRepository;
    protected final BaseMapper<D, E> mapper;

    protected BaseRepositoryAdapter(JpaRepository<E, UUID> jpaRepository, BaseMapper<D, E> mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<D> findById(UUID id) {
        Objects.requireNonNull(id, "id cannot be null");

        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<D> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public D save(D domain) {
        Objects.requireNonNull(domain, "domain cannot be null");
        E entity = mapper.toJpa(domain);

        Objects.requireNonNull(entity, "entity cannot be null");
        E saved = jpaRepository.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public D update(D domain) {
        Objects.requireNonNull(domain, "domain cannot be null");
        E entity = mapper.toJpa(domain);

        Objects.requireNonNull(entity, "entity cannot be null");
        E updated = jpaRepository.save(entity);

        return mapper.toDomain(updated);
    }

    @Override
    public void deleteById(UUID id) {
        Objects.requireNonNull(id, "id cannot be null");
        jpaRepository.deleteById(id);
    }
}
