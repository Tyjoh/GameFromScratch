package com.bytesmyth.lifegame.tilemap;

import com.bytesmyth.graphics.texture.TextureRegion;

public interface TileRenderer {

    TextureRegion render(int worldX, int worldY, Tile tile);

}
