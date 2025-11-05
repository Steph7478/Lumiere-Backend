package com.lumiere.infrastructure.persistence.utils;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Subgraph;
import java.util.List;
import java.util.Objects;

public final class EntityGraphBuilder {

    private EntityGraphBuilder() {
    }

    public static <T> EntityGraph<T> build(EntityManager em, Class<T> entityClass, List<String> paths) {
        Objects.requireNonNull(em, "EntityManager cannot be null");
        Objects.requireNonNull(entityClass, "EntityClass cannot be null");

        EntityGraph<T> graph = em.createEntityGraph(entityClass);

        if (paths == null || paths.isEmpty())
            return graph;

        for (String path : paths) {
            if (path != null && !path.isBlank()) {
                addSubgraph(graph, path.trim().split("\\."));
            }
        }

        return graph;
    }

    private static void addSubgraph(EntityGraph<?> graph, String[] parts) {
        Subgraph<?> parent = null;
        for (String part : parts) {
            part = part.trim();
            if (part.isEmpty())
                throw new IllegalArgumentException("Invalid path part in: " + String.join(".", parts));
            parent = (parent == null) ? graph.addSubgraph(part) : parent.addSubgraph(part);
        }
    }

    public static final String FETCHGRAPH_HINT = "jakarta.persistence.fetchgraph";
    public static final String LOADGRAPH_HINT = "jakarta.persistence.loadgraph";
}
