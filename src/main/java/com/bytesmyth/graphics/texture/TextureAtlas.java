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

        int numTiles = this.width * this.height;
        regions = new TextureRegion[numTiles];

        for (int ty = 0; ty < height; ty++) {
            for (int tx = 0; tx < width; tx++) {
                float u = tx * tileSizeU;
                float v = ty * tileSizeV;
                int id = tileCoordToId(tx, ty);
                regions[id] = new TextureRegion(u, v, u + tileSizeU, v + tileSizeV);
            }
        }

//        for (int i = 0; i < regions.length; i++) {
//            float tx = i % this.width;
//            float ty = i / this.height;
//
//            float u = tx * tileSizeU;
//            float v = ty * tileSizeV;
//            regions[i] = new TextureRegion(u, v, u + tileSizeU, v + tileSizeV);
//        }
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
