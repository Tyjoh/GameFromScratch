package com.bytesmyth.graphics.tileset;

public interface Tiler {

    TileDef tile(int x, int y, TileTypeProvider tileTypeProvider);

}
