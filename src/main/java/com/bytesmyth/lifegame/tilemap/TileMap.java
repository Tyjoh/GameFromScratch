package com.bytesmyth.lifegame.tilemap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TileMap {

    private Map<ChunkPosition, MapChunk> loadedChunks = new HashMap<>();
    private ChunkPosition accessor = new ChunkPosition(0, 0);

    public void addChunk(MapChunk chunk) {
        ChunkPosition position = new ChunkPosition(chunk.getChunkX(), chunk.getChunkY());
        this.loadedChunks.put(position, chunk);
    }

    public void removeChunk(int chunkX, int chunkY) {
        this.accessor.x = chunkX;
        this.accessor.y = chunkY;
        loadedChunks.remove(accessor);
    }

    public MapChunk getChunk(int chunkX, int chunkY) {
        this.accessor.x = chunkX;
        this.accessor.y = chunkY;
        return loadedChunks.get(accessor);
    }

    public Collection<MapChunk> getLoadedChunks() {
        return loadedChunks.values();
    }

    public MapChunk getChunkContaining(int tileX, int tileY) {
        ChunkPosition accessor = tileToChunk(tileX, tileY);
        return loadedChunks.get(accessor);
    }

    public TileMapLayer getLayer(String name) {
        return new TileMapLayer(name, this);
    }

    //TODO: this needs to be done when initializing a chunk
//    public ChunkLayer createLayer(String name) {
//        ChunkLayer layer = new ChunkLayer(size);
//        layers.put(name, layer);
//        return layer;
//    }

    public Tile getTile(String name, int tileX, int tileY) {
        MapChunk chunk = getChunkContaining(tileX, tileY);
        if (chunk == null) {
            return null;
        } else {
            return chunk.getTile(name, tileX, tileY);
        }
    }

    public void setTile(String layer, int x, int y, Tile tile) {
        ChunkPosition accessor = tileToChunk(x, y);
        MapChunk chunk = loadedChunks.get(accessor);
        if (chunk == null) {
            throw new RuntimeException(String.format("Chunk (%d, %d) is not loaded", accessor.x, accessor.y));
        } else {
            chunk.setTile(layer, x, y, tile);
        }
    }

    private ChunkPosition tileToChunk(int x, int y) {
        this.accessor.x = x / MapChunk.SIZE;
        if (x < 0) this.accessor.x--;
        this.accessor.y = y / MapChunk.SIZE;
        if (y < 0) this.accessor.y--;
        return this.accessor;
    }

    private static class ChunkPosition {

        private int x;
        private int y;

        private ChunkPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ChunkPosition that = (ChunkPosition) o;
            return x == that.x &&
                    y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
