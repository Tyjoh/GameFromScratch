package com.bytesmyth.lifegame.tilemap;

import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.graphics.texture.TextureRegion;

import java.util.Arrays;

public class NinePatchTileBuilder {

    private int tx;
    private int ty;
    private TextureAtlas atlas;

    public NinePatchTileBuilder(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    public NinePatchTileBuilder topLeft(int tx, int ty) {
        this.tx = tx;
        this.ty = ty;
        return this;
    }

    public TextureRegion[] buildTextureArray() {
        TextureRegion[] regions = new TextureRegion[16];
        Arrays.fill(regions, atlas.getRegionByCoord(tx + 1, ty + 1));

        //corners
        regions[1 + 2] = atlas.getRegionByCoord(tx, ty + 2);
        regions[2 + 4] = atlas.getRegionByCoord(tx, ty);
        regions[4 + 8] = atlas.getRegionByCoord(tx + 2, ty);
        regions[8 + 1] = atlas.getRegionByCoord(tx + 2, ty + 2);

        //sides
        regions[1 + 4 + 8] = atlas.getRegionByCoord(tx + 2, ty + 1);
        regions[1 + 4 + 2] = atlas.getRegionByCoord(tx, ty + 1);

        //top / bottom
        regions[2 + 4 + 8] = atlas.getRegionByCoord(tx + 1, ty);
        regions[1 + 2 + 8] = atlas.getRegionByCoord(tx + 1, ty + 2);

        return regions;
    }

}
