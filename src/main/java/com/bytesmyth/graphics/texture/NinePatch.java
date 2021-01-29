package com.bytesmyth.graphics.texture;

import com.bytesmyth.graphics.sprite.SpriteBatcher;
import com.bytesmyth.graphics.mesh.Rectangle;
import org.joml.Vector2f;

public class NinePatch {

    private final TextureRegion topLeft;
    private final TextureRegion left;
    private final TextureRegion bottomLeft;
    private final TextureRegion bottom;
    private final TextureRegion bottomRight;
    private final TextureRegion right;
    private final TextureRegion topRight;
    private final TextureRegion top;
    private final TextureRegion center;
    private final Texture texture;
    private int scale;

    public NinePatch(TextureAtlas atlas, int left, int top, int scale) {
        this.texture = atlas.getTexture();
        this.scale = scale;
        this.topLeft = atlas.getRegionByCoord(left, top);
        this.left = atlas.getRegionByCoord(left, top + 1);
        this.bottomLeft = atlas.getRegionByCoord(left, top + 2);
        this.bottom = atlas.getRegionByCoord(left + 1, top + 2);
        this.bottomRight = atlas.getRegionByCoord(left + 2, top + 2);
        this.right = atlas.getRegionByCoord(left + 2, top + 1);
        this.topRight = atlas.getRegionByCoord(left + 2, top);
        this.top = atlas.getRegionByCoord(left + 1, top);
        this.center = atlas.getRegionByCoord(left + 1, top + 1);
    }

    public void draw(float x, float y, float w, float h, SpriteBatcher batcher) {
        if (w < 1.3f || h < 1.3f) {
            throw new IllegalArgumentException("Must have width and height >= 1.3");
        }

        //corners
        batcher.draw(x, y-scale, x + scale, y, topLeft);
        batcher.draw(x + w - scale, y-scale, x + w, y, topRight);
        batcher.draw(x,y-h, x + scale, y-h+scale, bottomLeft);
        batcher.draw(x+w-scale, y-h, x + w, y-h+scale, bottomRight);

        //edges
        batcher.draw(x + scale, y-scale, x + w - scale, y, top);
        batcher.draw(x + scale, y-h, x + w - scale, y-h+scale, bottom);
        batcher.draw(x, y-h+scale, x + scale, y-scale, left);
        batcher.draw(x+w-scale, y-h+scale, x + w, y-scale, right);

        //center
        batcher.draw(x+scale, y-h+scale, x+w-scale, y-scale, center);
    }

    public Texture getTexture() {
        return texture;
    }
}
