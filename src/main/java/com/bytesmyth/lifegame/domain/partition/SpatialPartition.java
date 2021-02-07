package com.bytesmyth.lifegame.domain.partition;

import java.util.Set;

public interface SpatialPartition {

    PartitionRef partition(int entityId, float x, float y);

    /**
     * This function will return a set of entity id's where the set is guaranteed to have every
     * entity within the radius of x,y. This function may also include entities from outside of
     * the radius.
     * @param x
     * @param y
     * @param radius
     * @return set containing every entity within radius, and some entities near radius
     */
    Set<Integer> query(float x, float y, float radius);

    /**
     * This function will return a set of entities id's where every entity is garunteed to be
     * within radius.
     * @param x
     * @param y
     * @param radius
     * @return set containing every entity within radius, and some entities near radius
     */
    Set<Integer> queryExact(float x, float y, float radius);

}
