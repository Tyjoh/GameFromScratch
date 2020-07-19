package com.bytesmyth.testgame.tilemap;

import java.util.HashMap;
import java.util.Map;

public class TileMapChunk {

    public static final int SIZE = 64;

    private Map<String, TileLayer> layers = new HashMap<>();

    private int chunkX;
    private int chunkY;

    public TileMapChunk(int chunkX, int chunkY) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;

        addLayer(new TileLayer("ground", 0, SIZE));
        addLayer(new TileLayer("object1", -1, SIZE));
        addLayer(new TileLayer("object2", -1, SIZE));
        addLayer(new TileLayer("collision", 0, SIZE));
    }

    public TileLayer getLayer(String name) {
        return layers.get(name);
    }

    private void addLayer(TileLayer layer) {
        this.layers.put(layer.getLayerName(), layer);
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkY() {
        return chunkY;
    }
}
