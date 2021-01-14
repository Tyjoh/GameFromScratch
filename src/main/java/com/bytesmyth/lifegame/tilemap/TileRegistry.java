package com.bytesmyth.lifegame.tilemap;

import java.util.HashMap;
import java.util.Map;

public class TileRegistry {

    private Map<String, TileRenderer> nameToRenderer = new HashMap<>();

    public void registerRenderer(String name, TileRenderer renderer) {
        nameToRenderer.put(name, renderer);
    }

    public TileRenderer getRenderer(String name) {
        return nameToRenderer.get(name);
    }
}
