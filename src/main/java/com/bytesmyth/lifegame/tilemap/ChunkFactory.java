package com.bytesmyth.lifegame.tilemap;

public interface ChunkFactory {
    TileMapChunk create(int chunkX, int chunkY);
}
