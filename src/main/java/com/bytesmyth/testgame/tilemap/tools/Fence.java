package com.bytesmyth.testgame.tilemap.tools;

import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.testgame.tilemap.TileMap;

public class Fence {

    private int topLeft;
    private int left;
    private int bottomLeft;
    private int bottom;
    private int bottomRight;
    private int right;
    private int topRight;
    private int top;

    private int openingTop;
    private int openingBottom;
    private int openingLeft;
    private int openingRight;
    public static final String OBJECT_LAYER_1 = "object1";
    public static final String COLLISION_LAYER = "collision";

    public Fence(TextureAtlas atlas) {
        int centerX = 12;
        int centerY = 5;

        topLeft = atlas.tileCoordToId(centerX - 1, centerY - 1);
        left = atlas.tileCoordToId(centerX - 1, centerY);
        bottomLeft = atlas.tileCoordToId(centerX - 1, centerY + 1);
        bottom = atlas.tileCoordToId(centerX, centerY + 1);
        bottomRight = atlas.tileCoordToId(centerX + 1, centerY + 1);
        right = atlas.tileCoordToId(centerX + 1, centerY);
        topRight = atlas.tileCoordToId(centerX + 1, centerY - 1);
        top = atlas.tileCoordToId(centerX, centerY - 1);

        openingTop = atlas.tileCoordToId(14, 6);
        openingBottom = atlas.tileCoordToId(14, 5);

        openingLeft = atlas.tileCoordToId(15, 4);
        openingRight = atlas.tileCoordToId(14, 4);

    }

    public void border(int left, int top, int right, int bottom, TileMap map) {

        for (int x = left + 1; x < right; x++) {
            map.set(OBJECT_LAYER_1, x, top, this.top);
            map.set(COLLISION_LAYER, x, top, 1);
            map.set(OBJECT_LAYER_1, x, bottom, this.bottom);
            map.set(COLLISION_LAYER, x, bottom, 1);
        }

        for (int y = bottom + 1; y < top; y++) {
            map.set(OBJECT_LAYER_1, left, y, this.left);
            map.set(COLLISION_LAYER, left, y, 1);
            map.set(OBJECT_LAYER_1, right, y, this.right);
            map.set(COLLISION_LAYER, right, y, 1);
        }

        map.set(OBJECT_LAYER_1, left, top, topLeft);
        map.set(OBJECT_LAYER_1, right, top, topRight);
        map.set(OBJECT_LAYER_1, right, bottom, bottomRight);
        map.set(OBJECT_LAYER_1, left, bottom, bottomLeft);

        map.set(COLLISION_LAYER, left, top, 1);
        map.set(COLLISION_LAYER, right, top, 1);
        map.set(COLLISION_LAYER, right, bottom, 1);
        map.set(COLLISION_LAYER, left, bottom, 1);
    }

    public void verticalOpening(int x, int y, TileMap map) {
        map.set(OBJECT_LAYER_1, x, y, -1);
        map.set(COLLISION_LAYER, x, y, 0);

        map.set(OBJECT_LAYER_1, x, y + 1, openingTop);
        map.set(COLLISION_LAYER, x, y + 1, 1);
        map.set(OBJECT_LAYER_1, x, y - 1, openingBottom);
        map.set(COLLISION_LAYER, x, y - 1, 1);
    }

    public void horizontalOpening(int x, int y, TileMap map) {
        map.set(OBJECT_LAYER_1, x, y, -1);
        map.set(COLLISION_LAYER, x, y, 0);

        map.set(OBJECT_LAYER_1, x + 1, y, openingRight);
        map.set(COLLISION_LAYER, x + 1, y, 1);
        map.set(OBJECT_LAYER_1, x - 1, y, openingLeft);
        map.set(COLLISION_LAYER, x - 1, y, 1);
    }
}
