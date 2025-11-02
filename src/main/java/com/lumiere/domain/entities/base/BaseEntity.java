package com.lumiere.domain.entities.base;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseEntity {

    private final UUID id;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected BaseEntity() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof BaseEntity))
            return false;
        BaseEntity other = (BaseEntity) obj;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
