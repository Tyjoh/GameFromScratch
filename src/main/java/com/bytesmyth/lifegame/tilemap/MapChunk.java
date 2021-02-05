package com.bytesmyth.lifegame.tilemap;

import java.util.HashMap;
import java.util.Map;

public class MapChunk {
    public static final int SIZE = 64;

    private Map<String, ChunkLayer> layers = new HashMap<>();
    private final int size;

    private final int chunkX;
    private final int chunkY;

    public MapChunk(int chunkX, int chunkY) {
        this.size = SIZE;
        this.chunkX = chunkX;
        this.chunkY = chunkY;
    }

    public int getSize() {
        return size;
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkY() {
        return chunkY;
    }

    public ChunkLayer getLayer(String name) {
        return layers.get(name);
    }

    public ChunkLayer createLayer(String name) {
        ChunkLayer layer = new ChunkLayer(size);
        layers.put(name, layer);
        return layer;
    }

    public Tile getTile(String name, int tileX, int tileY) {
        ChunkLayer layer = layers.get(name);
        if (layer != null) {
            return layer.getTile(toLocalX(tileX), toLocalY(tileY));
        } else {
            return null;
        }
    }

    public void setTile(String layer, int tileX, int tileY, Tile tile) {
        ChunkLayer l = this.getLayer(layer);
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
