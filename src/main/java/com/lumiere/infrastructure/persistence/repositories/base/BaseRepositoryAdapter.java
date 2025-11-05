package com.lumiere.infrastructure.persistence.repositories.base;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.utils.EntityGraphBuilder;

public abstract class BaseRepositoryAdapter<D, E> implements BaseReader<D>, BaseWriter<D> {

    protected final JpaRepository<E, UUID> jpaRepository;
    protected final BaseMapper<D, E> mapper;
    protected final EntityManager entityManager;
    private final Class<E> entityClass;

    protected BaseRepositoryAdapter(
            JpaRepository<E, UUID> jpaRepository,
            BaseMapper<D, E> mapper,
            EntityManager entityManager,
            Class<E> entityClass) {
        this.jpaRepository = Objects.requireNonNull(jpaRepository, "jpaRepository cannot be null");
        this.mapper = Objects.requireNonNull(mapper, "mapper cannot be null");
        this.entityManager = Objects.requireNonNull(entityManager, "entityManager cannot be null");
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass cannot be null");
    }

    @Override
    public Optional<D> findById(UUID id) {
        Objects.requireNonNull(id, "id cannot be null");
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<D> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    public List<D> findAllWithEager(String... relations) {
        if (relations == null)
            relations = new String[0];

        EntityGraph<E> graph = EntityGraphBuilder.build(entityManager, entityClass, List.of(relations));

        String jpql = "SELECT e FROM " + entityClass.getName() + " e";
        TypedQuery<E> query = entityManager.createQuery(jpql, entityClass);
        query.setHint(EntityGraphBuilder.FETCHGRAPH_HINT, graph);

        return query.getResultList().stream()
                .map(mapper::toDomain)
                .toList();
    }

    public Optional<D> findByIdWithEager(UUID id, String... relations) {
        Objects.requireNonNull(id, "ID cannot be null");
        if (relations == null)
            relations = new String[0];

        EntityGraph<E> graph = EntityGraphBuilder.build(entityManager, entityClass, List.of(relations));

        E entity;
        try {
            entity = entityManager.find(entityClass, id, Map.of(EntityGraphBuilder.FETCHGRAPH_HINT, graph));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Error fetching entity with invalid paths: " + Arrays.toString(relations), ex);
        }

        return Optional.ofNullable(entity)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional
    public D save(D domain) {
        Objects.requireNonNull(domain, "domain cannot be null");
        E entity = Objects.requireNonNull(mapper.toJpa(domain), "entity cannot be null");
        E saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional
    public D update(D domain) {
        Objects.requireNonNull(domain, "domain cannot be null");
        E entity = Objects.requireNonNull(mapper.toJpa(domain), "entity cannot be null");
        E updated = jpaRepository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        Objects.requireNonNull(id, "id cannot be null");
        jpaRepository.deleteById(id);
    }
}