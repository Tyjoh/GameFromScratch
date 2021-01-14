package com.bytesmyth.lifegame.tilemap;

import com.bytesmyth.graphics.texture.TextureRegion;

public class StaticTileRenderer  implements TileRenderer {

    private TextureRegion region;

    public StaticTileRenderer(TextureRegion region) {
        this.region = region;
    }

    @Override
    public TextureRegion render(int x, int y, Tile tile) {
        return region;
    }
}
