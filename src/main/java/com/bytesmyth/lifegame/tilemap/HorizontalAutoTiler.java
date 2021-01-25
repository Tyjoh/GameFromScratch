package com.bytesmyth.lifegame.tilemap;

import com.bytesmyth.graphics.texture.TextureRegion;

public class HorizontalAutoTiler implements Tiler {

    private String type;
    private TextureRegion[] tiles;

    public HorizontalAutoTiler(String type, TextureRegion[] tiles) {
        if (tiles.length != 4) throw new IllegalArgumentException("Horizontal auto tilers require 4 textures");
        this.type = type;
        this.tiles = tiles;
    }

    public void tile(int x, int y, TileMapLayer layer) {
        Tile tile = layer.getTile(x, y);
        if (tile == null || !tile.getType().equals(type)) {
            return;
        }

        int kern = kern(x, y, layer);
        tile.setTextureRegion(tiles[kern]);
    }

    private int kern(int x, int y, TileMapLayer layer) {
        Tile left = layer.getTile(x - 1, y);
        Tile right = layer.getTile(x + 1, y);

        int kern = 0;

        if (left != null && left.getType().equals(type)) {
            kern += 1;
        }

        if (right != null && right.getType().equals(type)) {
            kern += 2;
        }

        return kern;
    }

}
