package com.bytesmyth.graphics.texture;

public class TextureAtlas {

    private final Texture texture;

    private final int width;
    private final int height;

    private TextureRegion[] regions;

    public TextureAtlas(Texture texture, int tileWidth, int tileHeight) {
        this.texture = texture;

        this.width = texture.getWidth() / tileWidth;
        this.height = texture.getHeight() / tileHeight;

        float tileSizeU = (float)tileWidth / texture.getWidth();
        float tileSizeV = (float)tileHeight / texture.getHeight();

        float paddingW = 0.01f / texture.getWidth();
        float paddingH = 0.01f / texture.getHeight();

        int numTiles = this.width * this.height;
        regions = new TextureRegion[numTiles];

        for (int ty = 0; ty < height; ty++) {
            for (int tx = 0; tx < width; tx++) {
                float u = tx * tileSizeU;
                float v = ty * tileSizeV;
                int id = tileCoordToId(tx, ty);
                regions[id] = new TextureRegion(u + paddingW, v + paddingH, u + tileSizeU - paddingW, v + tileSizeV - paddingH);
            }
        }
    }

    public TextureRegion getRegionById(int id) {
        return regions[id];
    }

    public Texture getTexture() {
        return texture;
    }

    public int tileCoordToId(int x, int y) {
        return y * width + x;
    }

    public TextureRegion getRegionByCoord(int x, int y) {
        return getRegionById(tileCoordToId(x, y));
    }
}
