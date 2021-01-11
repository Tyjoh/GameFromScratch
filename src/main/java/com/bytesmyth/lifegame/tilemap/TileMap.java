package com.bytesmyth.lifegame.tilemap;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TileMap {

    private Map<ChunkPosition, TileMapChunk> chunks = new HashMap<>();
    private ChunkPosition lookup = new ChunkPosition(0,0);
    private ChunkFactory chunkGenerator;

    private TileRegistry tileRegistry = new TileRegistry();

    public TileMap(ChunkFactory chunkGenerator) {
        this.chunkGenerator = chunkGenerator;
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                chunks.put(new ChunkPosition(x, y), chunkGenerator.create(x, y));
            }
        }
    }

    public TileRegistry getTileRegistry() {
        return tileRegistry;
    }

    public int get(String layer, int x, int y) {
        TileLayer tileLayer = getChunk(x, y).getLayer(layer);
        int lx = toChunkOffset(x);
        int ly = toChunkOffset(y);
        return tileLayer.getTileId(lx, ly);
    }

    public void set(String layer, int x, int y, int tileId) {
        TileLayer tileLayer = getChunk(x, y).getLayer(layer);
        int lx = toChunkOffset(x);
        int ly = toChunkOffset(y);
        tileLayer.setTile(lx, ly, tileId);
    }

    public void set(String layer, int x, int y, String name) {
        TileLayer tileLayer = getChunk(x, y).getLayer(layer);
        int lx = toChunkOffset(x);
        int ly = toChunkOffset(y);
        tileLayer.setTile(lx, ly, tileRegistry.toId(name));
    }

    private TileMapChunk getChunk(int mapX, int mapY) {
        lookup.set(mapX, mapY);
        if (chunks.containsKey(lookup)) {
            return chunks.get(lookup);
        } else {
            TileMapChunk chunk = chunkGenerator.create(mapX, mapY);
            chunks.put(lookup, chunk);
            return chunk;
        }
    }

    private int toChunkOffset(int v) {
        if (v >= 0) {
            return v % TileMapChunk.SIZE;
        } else {
            return ((v + 1) % TileMapChunk.SIZE) + (TileMapChunk.SIZE - 1);
        }
    }

    private static class ChunkPosition {
        private int x;
        private int y;

        public ChunkPosition(int mapX, int mapY) {
            set(mapX, mapY);
        }

        public void set(int mapX, int mapY) {
            this.x = mapX >= 0 ? mapX / TileMapChunk.SIZE : (mapX / TileMapChunk.SIZE) - 1;
            this.y = mapY >= 0 ? mapY / TileMapChunk.SIZE : (mapY / TileMapChunk.SIZE) - 1;
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
