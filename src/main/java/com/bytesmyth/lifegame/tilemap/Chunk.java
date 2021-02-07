package com.bytesmyth.lifegame.tilemap;

import com.bytesmyth.lifegame.domain.partition.PartitionRef;
import com.bytesmyth.lifegame.domain.partition.PartitionedEntity;

import java.util.*;

public class Chunk implements PartitionRef {
    public static final int SIZE = 32;

    private final Map<String, Layer> layers = new HashMap<>();
    private final int size;

    private final int chunkX;
    private final int chunkY;

    private final Map<Integer, PartitionedEntity> entities = new HashMap<>();

    public Chunk(int chunkX, int chunkY) {
        this.size = SIZE;
        this.chunkX = chunkX;
        this.chunkY = chunkY;
    }

    public void addEntity(PartitionedEntity e) {
        this.entities.put(e.getEntityId(), e);
    }

    public void removeEntity(PartitionedEntity e) {
        this.entities.remove(e.getEntityId());
    }

    public Collection<PartitionedEntity> getEntities() {
        return this.entities.values();
    }

    @Override
    public void unPartition(int entityId) {
        this.entities.remove(entityId);
    }

    public int getSize() {
        return size;
    }

    @Override
    public int getX() {
        return chunkX;
    }

    @Override
    public int getY() {
        return chunkY;
    }

    public Layer getLayer(String name) {
        return layers.get(name);
    }

    public Layer createLayer(String name) {
        Layer layer = new Layer(size);
        layers.put(name, layer);
        return layer;
    }

    public Tile getTile(String name, int tileX, int tileY) {
        Layer layer = layers.get(name);
        if (layer != null) {
            return layer.getTile(toLocalX(tileX), toLocalY(tileY));
        } else {
            return null;
        }
    }

    public void setTile(String layer, int tileX, int tileY, Tile tile) {
        Layer l = this.getLayer(layer);
        if (l != null) {
            l.setTile(toLocalX(tileX), toLocalY(tileY), tile);
        } else {
            throw new RuntimeException("Layer " + layer + " does not exist");
        }
    }

    public int toLocalX(int tileX) {
        return tileX - this.chunkX * size;
    }

    public int toLocalY(int tileY) {
        return tileY - this.chunkY * size;
    }

    public int localToTileX(int localX) {
        return this.chunkX * size + localX;
    }

    public int localToTileY(int localY) {
        return this.chunkY * size + localY;
    }
}
