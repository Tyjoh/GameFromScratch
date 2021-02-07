package com.bytesmyth.graphics.tileset;

import com.bytesmyth.graphics.texture.TextureRegion;

import java.util.Arrays;

public class NinePatchTileBuilder {

    private int tx;
    private int ty;
    private Tileset atlas;

    public NinePatchTileBuilder(Tileset tileset) {
        this.atlas = tileset;
    }

    public NinePatchTileBuilder topLeft(int tx, int ty) {
        this.tx = tx;
        this.ty = ty;
        return this;
    }

    public TextureRegion[] buildTextureArray() {
        TextureRegion[] regions = new TextureRegion[16];
        Arrays.fill(regions, atlas.getTile(tx + 1, ty + 1).getRegion());

        //corners
        regions[1 + 2] = atlas.getTile(tx, ty + 2).getRegion();
        regions[2 + 4] = atlas.getTile(tx, ty).getRegion();
        regions[4 + 8] = atlas.getTile(tx + 2, ty).getRegion();
        regions[8 + 1] = atlas.getTile(tx + 2, ty + 2).getRegion();

        //sides
        regions[1 + 4 + 8] = atlas.getTile(tx + 2, ty + 1).getRegion();
        regions[1 + 4 + 2] = atlas.getTile(tx, ty + 1).getRegion();

        //top / bottom
        regions[2 + 4 + 8] = atlas.getTile(tx + 1, ty).getRegion();
        regions[1 + 2 + 8] = atlas.getTile(tx + 1, ty + 2).getRegion();

        return regions;
    }

}
