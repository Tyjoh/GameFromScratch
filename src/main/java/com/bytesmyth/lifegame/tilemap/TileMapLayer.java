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

    public String getTileType(int x, int y) {
        Tile tile = getTile(x, y);
        if (tile != null) {
            return tile.getType();
        } else {
            return null;
        }
    }

    public void setTile(int x, int y, Tile tile) {
        map.setTile(layer, x, y, tile);
    }

}
