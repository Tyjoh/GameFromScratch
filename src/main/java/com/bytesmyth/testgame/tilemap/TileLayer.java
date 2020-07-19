package com.bytesmyth.testgame.tilemap;

import java.util.Arrays;

public class TileLayer {

    private String layerName;
    private int size;
    private int[][] tiles;

    public TileLayer(String layerName, int defaultId, int size) {
        this.layerName = layerName;
        this.size = size;
        tiles = new int[size][size];
        this.setAll(defaultId);
    }

    public int getTileId(int x, int y) {
        assert x >= 0 && x < size;
        assert y >= 0 && y < size;

        return tiles[x][y];
    }

    public void setTile(int x, int y, int tileId) {
        assert x >= 0 && x < size;
        assert y >= 0 && y < size;

        tiles[x][y] = tileId;
    }

    public int getWidth() {
        return size;
    }

    public int getHeight() {
        return size;
    }

    public void setAll(int i) {
        for (int[] ints : tiles) {
            Arrays.fill(ints, i);
        }
    }

    public String getLayerName() {
        return layerName;
    }
}
