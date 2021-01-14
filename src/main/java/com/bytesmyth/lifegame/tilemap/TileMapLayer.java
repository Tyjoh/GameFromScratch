package com.bytesmyth.lifegame.tilemap;

public class TileMapLayer {

    public static final int SIZE = 64;

    private final int size;
    private final Tile[][] tiles;
    private final TileMap map;

    public TileMapLayer(TileMap map, int size) {
        this.map = map;
        this.size = SIZE;
        tiles = new Tile[size][size];
    }

    public TileMap getMap() {
        return map;
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= getWidth()) return null;
        if (y < 0 || y >= getHeight()) return null;
        return tiles[x][y];
    }

    public void setTile(int x, int y, Tile tile) {
        assert x >= 0 && x < getWidth();
        assert y >= 0 && y < getHeight();

        tiles[x][y] = tile;
        if (tile != null) {
            tile.setLayer(this);
        }
    }

    public int getWidth() {
        return size;
    }

    public int getHeight() {
        return size;
    }
}
