package com.bytesmyth.lifegame.domain.partition;

import java.util.Objects;

public class PartitionedEntity {
    private int entity;
    private float x;
    private float y;

    public PartitionedEntity set(int entity, float x, float y) {
        this.entity = entity;
        this.x = x;
        this.y = y;
        return this;
    }

    public int getEntityId() {
        return entity;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean within(float tx, float ty, float radSquared) {
        float dx = tx - x;
        float dy = ty - y;
        return (dx * dx) + (dy * dy) <= radSquared;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartitionedEntity that = (PartitionedEntity) o;
        return entity == that.entity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity);
    }
}
