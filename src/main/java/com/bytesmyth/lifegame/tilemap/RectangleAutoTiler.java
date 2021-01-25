package com.bytesmyth.lifegame.tilemap;

import com.bytesmyth.graphics.texture.TextureRegion;

public class RectangleAutoTiler implements Tiler {

    public static final int TOP_LEFT = 1;
    public static final int TOP = 2;
    public static final int TOP_RIGHT = 4;
    public static final int RIGHT = 8;
    public static final int BOTTOM_RIGHT = 16;
    public static final int BOTTOM = 32;
    public static final int BOTTOM_LEFT = 64;
    public static final int LEFT = 128;

    private final String type;
    private final String maskType;
    private final TextureRegion[] tiles;

    private final MaskTile topLeft;
    private final MaskTile top;
    private final MaskTile topRight;
    private final MaskTile right;
    private final MaskTile bottomRight;
    private final MaskTile bottom;
    private final MaskTile bottomLeft;
    private final MaskTile left;

    public RectangleAutoTiler(String type, String maskType, TextureRegion[] tiles) {
        this.maskType = maskType;
        if (tiles.length != 256) throw new IllegalArgumentException("Horizontal auto tilers require 256 texture regions");
        this.type = type;
        this.tiles = tiles;

        topLeft = new MaskTile(-1, 1, TOP_LEFT, maskType);
        top = new MaskTile(0, 1, TOP, maskType);
        topRight = new MaskTile(1, 1, TOP_RIGHT, maskType);
        right = new MaskTile(1, 0, RIGHT, maskType);
        bottomRight = new MaskTile(1, -1, BOTTOM_RIGHT, maskType);
        bottom = new MaskTile(0, -1, BOTTOM, maskType);
        bottomLeft = new MaskTile(-1, -1, BOTTOM_LEFT, maskType);
        left = new MaskTile(-1, 0, LEFT, maskType);
    }

    public RectangleAutoTiler(String type, TextureRegion[] tiles) {
        this(type, type, tiles);
    }

    public void tile(int x, int y, TileMapLayer layer) {
        Tile tile = layer.getTile(x, y);
        if (tile == null || !tile.getType().equals(type)) {
            return;
        }

        int kern = 0;

        int top = this.top.kern(x, y, layer);
        kern |= top;

        int right = this.right.kern(x, y, layer);
        kern |= right;

        int bottom = this.bottom.kern(x, y, layer);
        kern |= bottom;

        int left = this.left.kern(x, y, layer);
        kern |= left;

        //this is to not count corners unless there is a direct tile connecting them.
        if (top + left != 0) {
            kern |= topLeft.kern(x, y, layer);
        }

        if (top + right != 0) {
            kern |= topRight.kern(x, y, layer);
        }

        if (bottom + right != 0) {
            kern |= bottomRight.kern(x, y, layer);
        }

        if (bottom + left != 0) {
            kern |= bottomLeft.kern(x, y, layer);
        }

        TextureRegion region = tiles[kern];
        layer.getTile(x, y).setTextureRegion(region);
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
