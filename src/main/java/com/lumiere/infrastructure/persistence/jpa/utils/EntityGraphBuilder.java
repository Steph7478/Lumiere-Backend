package com.lumiere.infrastructure.persistence.jpa.utils;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Subgraph;
import java.util.List;
import java.util.Objects;

public final class EntityGraphBuilder {

    public static final String FETCHGRAPH_HINT = "jakarta.persistence.fetchgraph";
    public static final String LOADGRAPH_HINT = "jakarta.persistence.loadgraph";

    private EntityGraphBuilder() {
    }

    public static <T> EntityGraph<T> build(EntityManager em, Class<T> entityClass, List<String> paths) {
        Objects.requireNonNull(em, "EntityManager cannot be null");
        Objects.requireNonNull(entityClass, "EntityClass cannot be null");

        EntityGraph<T> graph = em.createEntityGraph(entityClass);

        if (paths != null) {
            for (String path : paths) {
                if (path != null && !path.isBlank()) {
                    addSubgraph(graph, path.trim().split("\\."));
                }
            }
        }

        return graph;
    }

    private static void addSubgraph(EntityGraph<?> graph, String[] parts) {
        Subgraph<?> currentGraph = null;
        for (String part : parts) {
            String trimmedPart = part.trim();
            if (trimmedPart.isEmpty()) {
                throw new IllegalArgumentException("Invalid path part in: " + String.join(".", parts));
            }

            currentGraph = (currentGraph == null)
                    ? graph.addSubgraph(trimmedPart)
                    : currentGraph.addSubgraph(trimmedPart);
        }
    }
}