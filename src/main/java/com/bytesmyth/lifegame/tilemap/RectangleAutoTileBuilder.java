package com.bytesmyth.lifegame.tilemap;

import com.bytesmyth.graphics.texture.TextureRegion;

import java.util.*;

public class RectangleAutoTileBuilder {

    private TextureRegion[] tiles = new TextureRegion[256];
    private String type;
    private String matchType;

    private int[] values = new int[256];

    public RectangleAutoTileBuilder(String type, TextureRegion defaultTile) {
        this(type, type, defaultTile);
    }

    public RectangleAutoTileBuilder(String type, String matchType, TextureRegion defaultTile) {
        this.type = type;
        this.matchType = matchType;

        Arrays.fill(tiles, defaultTile);
        for (int i = 0; i < values.length; i++) {
            values[i] = i;
        }
    }

    public RectangleAutoTileBuilder requireAndForbid(TextureRegion region, int required, int forbidden) {
        for (int value : values) {
            if ((value & forbidden) != 0) continue;
            if ((value & required) != required) continue;

            tiles[value] = region;
        }

        return this;
    }

    public RectangleAutoTiler build() {
        if (matchType == null) {
            return new RectangleAutoTiler(type, tiles);
        } else {
            return new RectangleAutoTiler(type, matchType, tiles);
        }
    }

}
