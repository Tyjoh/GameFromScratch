package com.bytesmyth.lifegame.tilemap.tools;

import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.lifegame.tilemap.TileMap;

import java.util.*;

public class Prefab {

    private String name;

    public Set<Tile> tiles = new HashSet<>();

    public Prefab(String name) {
        this.name = name;
    }

    public static Prefab merge(String newName, Prefab... prefabs) {
        Prefab merged = new Prefab(newName);
        for (Prefab prefab : prefabs) {
            merged.tiles.addAll(prefab.tiles);
        }
        return merged;
    }

    public String getName() {
        return name;
    }

    public void apply(int x, int y, TileMap map) {
        for (Tile tile : tiles) {
            map.set(tile.layer,x + tile.xOffset, y + tile.yOffset, tile.id);
        }
    }

    public void add(String layer, int id, int xOffset, int yOffset) {
        tiles.add(new Tile(layer, id, xOffset, yOffset));
    }

    public void addRect(String layer, int left, int top, int right, int bottom, TextureAtlas atlas, int xOffset, int yOffset) {
        int width = right - left;
        int height = bottom - top;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles.add(new Tile(layer, atlas.tileCoordToId(left + x, bottom - y - 1), x + xOffset, y + yOffset));
            }
        }
    }

    public void solidify() {
        List<Tile> solids = new ArrayList<>(tiles.size());
        for (Tile tile : tiles) {
            solids.add(new Tile("collision", 1, tile.xOffset, tile.yOffset));
        }
        tiles.addAll(solids);
    }

    private static class Tile {
        private String layer;
        private int id;
        private int xOffset;
        private int yOffset;

        Tile(String layer, int id, int xOffset, int yOffset) {
            this.layer = layer;
            this.id = id;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tile tile = (Tile) o;
            return xOffset == tile.xOffset &&
                    yOffset == tile.yOffset &&
                    Objects.equals(layer, tile.layer);
        }

        @Override
        public int hashCode() {
            return Objects.hash(layer, xOffset, yOffset);
        }
    }
}
