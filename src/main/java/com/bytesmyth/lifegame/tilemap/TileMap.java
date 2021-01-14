package com.bytesmyth.lifegame.tilemap;

import java.util.HashMap;
import java.util.Map;

public class TileMap {

    private Map<String, TileMapLayer> layers = new HashMap<>();
    private int size;

    public TileMap(int size) {
        this.size = size;
    }

    public TileMapLayer getLayer(String name) {
        return layers.get(name);
    }

    public TileMapLayer createLayer(String name) {
        TileMapLayer layer = new TileMapLayer(this, size);
        layers.put(name, layer);
        return layer;
    }

    public Tile getTile(String name, int x, int y) {
        TileMapLayer layer = layers.get(name);
        if (layer != null) {
            return layer.getTile(x, y);
        } else {
            return null;
        }
    }

    public int getWidth() {
        return size;
    }

    public int getHeight() {
        return size;
    }
}
