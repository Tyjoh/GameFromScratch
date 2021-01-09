package com.bytesmyth.lifegame.tilemap;

public class TileMapChunkGenerator implements ChunkFactory {

    public TileMapChunk create(int chunkX, int chunkY) {
        return new TileMapChunk(chunkX, chunkY);
    }

}
