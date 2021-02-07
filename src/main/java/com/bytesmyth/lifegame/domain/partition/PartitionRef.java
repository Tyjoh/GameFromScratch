package com.bytesmyth.lifegame.domain.partition;

public interface PartitionRef {
    int getX();
    int getY();
    void unPartition(int entityId);
}
