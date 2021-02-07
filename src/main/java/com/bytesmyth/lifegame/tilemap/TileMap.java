package com.bytesmyth.lifegame.tilemap;

import com.bytesmyth.lifegame.domain.partition.PartitionRef;
import com.bytesmyth.lifegame.domain.partition.PartitionedEntity;
import com.bytesmyth.lifegame.domain.partition.SpatialPartition;
import com.bytesmyth.util.Pool;

import java.util.*;

public class TileMap implements SpatialPartition {

    private final Map<ChunkPosition, Chunk> loadedChunks = new HashMap<>();
    private final ChunkPosition accessor = new ChunkPosition(0, 0);

    private final Pool<PartitionedEntity> partitionedEntityPool = new Pool<>(PartitionedEntity::new, 1000);

    public void addChunk(Chunk chunk) {
        ChunkPosition position = new ChunkPosition(chunk.getX(), chunk.getY());
        this.loadedChunks.put(position, chunk);
    }

    public void removeChunk(int chunkX, int chunkY) {
        this.accessor.x = chunkX;
        this.accessor.y = chunkY;
        loadedChunks.remove(accessor);
    }

    public Chunk getChunk(int chunkX, int chunkY) {
        this.accessor.x = chunkX;
        this.accessor.y = chunkY;
        return loadedChunks.get(accessor);
    }

    public Collection<Chunk> getLoadedChunks() {
        return loadedChunks.values();
    }

    public Chunk getChunkContaining(float tileX, float tileY) {
        ChunkPosition accessor = tileToChunk(tileX, tileY);
        return loadedChunks.get(accessor);
    }

    public TileMapLayer getLayer(String name) {
        return new TileMapLayer(name, this);
    }

    public Tile getTile(String name, int tileX, int tileY) {
        Chunk chunk = getChunkContaining(tileX, tileY);
        if (chunk == null) {
            return null;
        } else {
            return chunk.getTile(name, tileX, tileY);
        }
    }

    public void setTile(String layer, int x, int y, Tile tile) {
        ChunkPosition accessor = tileToChunk(x, y);
        Chunk chunk = loadedChunks.get(accessor);
        if (chunk == null) {
            throw new RuntimeException(String.format("Chunk (%d, %d) is not loaded", accessor.x, accessor.y));
        } else {
            chunk.setTile(layer, x, y, tile);
        }
    }

    private ChunkPosition tileToChunk(float x, float y) {
        this.accessor.x = (int) (x / Chunk.SIZE);
        if (x < 0) this.accessor.x--;
        this.accessor.y = (int) (y / Chunk.SIZE);
        if (y < 0) this.accessor.y--;
        return this.accessor;
    }

    @Override
    public PartitionRef partition(int entityId, float x, float y) {
        Chunk chunk = getChunkContaining((int) x, (int) y);
        chunk.addEntity(partitionedEntityPool.get().set(entityId, x, y));
        return chunk;
    }

    @Override
    public Set<Integer> query(float x, float y, float radius) {
        Set<Integer> hashset = new HashSet<>();
        List<Chunk> chunks = getIntersectingChunks(x, y, radius);
        for (Chunk chunk : chunks) {
            chunk.getEntities().stream()
                    .map(PartitionedEntity::getEntityId)
                    .forEach(hashset::add);
        }
        return hashset;
    }

    @Override
    public Set<Integer> queryExact(float x, float y, float radius) {
        float rad2 = radius * radius;

        Set<Integer> hashset = new HashSet<>();
        List<Chunk> chunks = getIntersectingChunks(x, y, radius);

        for (Chunk chunk : chunks) {
            chunk.getEntities().stream()
                    .filter(e -> e.within(x, y, rad2))
                    .map(PartitionedEntity::getEntityId)
                    .forEach(hashset::add);
        }

        return hashset;
    }

    private List<Chunk> getIntersectingChunks(float x, float y, float radius) {
        float querySpanChunks = (radius * 2) / Chunk.SIZE;
        int searchSquare = (int) (Math.floor(querySpanChunks) + 1);

        List<Chunk> chunks = new ArrayList<>(searchSquare * searchSquare);

        for (float tx = x - radius; tx < x + radius; tx += Chunk.SIZE) {
            for (float ty = y - radius; ty < y + radius; ty += Chunk.SIZE) {
                chunks.add(getChunkContaining(tx, ty));
            }
        }

        return chunks;
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
