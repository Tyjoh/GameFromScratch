package com.bytesmyth.lifegame.tilemap;

public class TileMapLayer {

    private String layer;
    private TileMap map;

    public TileMapLayer(String layer, TileMap map) {
        this.layer = layer;
        this.map = map;
    }

    public Tile getTile(int x, int y) {
        return map.getTile(layer, x, y);
    }

    public void setTile(int x, int y, Tile tile) {
        map.setTile(layer, x, y, tile);
    }

}
