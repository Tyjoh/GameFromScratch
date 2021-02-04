package com.bytesmyth.lifegame.tilemap;

import com.bytesmyth.graphics.texture.TextureRegion;

public class QuadAutoTiler {

    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 4;
    public static final int LEFT = 8;

    private final String type;
    private final String maskType;
    private final TextureRegion[] tiles;

    private final MaskTile top;
    private final MaskTile right;
    private final MaskTile bottom;
    private final MaskTile left;

    public QuadAutoTiler(String type, String maskType, TextureRegion[] tiles) {
        this.maskType = maskType;
        if (tiles.length != 256) throw new IllegalArgumentException("Horizontal auto tilers require 256 texture regions");
        this.type = type;
        this.tiles = tiles;

        top = new MaskTile(0, 1, TOP, maskType);
        right = new MaskTile(1, 0, RIGHT, maskType);
        bottom = new MaskTile(0, -1, BOTTOM, maskType);
        left = new MaskTile(-1, 0, LEFT, maskType);
    }

    public void tile(int x, int y, TileMap map) {

    }

    private static class MaskTile {
        private int x;
        private int y;
        private int bitmask;
        private String type;

        private MaskTile(int x, int y, int mask, String type) {
            this.x = x;
            this.y = y;
            this.bitmask = mask;
            this.type = type;
        }

        private int kern(int x, int y, TileMapLayer layer) {
            Tile tile = layer.getTile(x + this.x, y + this.y);

            if (tile != null && tile.getType().equals(type)) {
                return bitmask;
            } else {
                return 0;
            }
        }
    }

}
