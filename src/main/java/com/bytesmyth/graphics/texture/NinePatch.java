package com.bytesmyth.graphics.texture;

import com.bytesmyth.graphics.batch.QuadTextureBatcher;
import com.bytesmyth.graphics.mesh.Rectangle;
import org.joml.Vector2f;
import org.joml.Vector4f;

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
    private final TextureAtlas atlas;

    public NinePatch(TextureAtlas atlas, int left, int top) {
        this.atlas = atlas;

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

    public void draw(float x, float y, float w, float h, QuadTextureBatcher batcher) {
        if (w < 1.3f || h < 1.3f) {
            throw new IllegalArgumentException("Must have width and height >= 1.3");
        }

        float scale = 16;

        Rectangle corner = new Rectangle(scale, scale);
        Rectangle verticalMiddle = new Rectangle(w - scale * 2, scale);
        Rectangle horizontalMiddle = new Rectangle(scale, h - scale * 2);
        Rectangle center = new Rectangle(w - scale * 2, h - scale * 2);

        Vector2f position = new Vector2f();
        batcher.draw(corner, topLeft, position.set(x + corner.getHalfWidth(), y - corner.getHalfHeight()));
        batcher.draw(corner, topRight, position.set(x + w - corner.getHalfWidth(), y - corner.getHalfHeight()));
        batcher.draw(corner, bottomLeft, position.set(x + corner.getHalfWidth(), y - h + corner.getHalfHeight()));
        batcher.draw(corner, bottomRight, position.set(x + w - corner.getHalfWidth(), y - h + corner.getHalfHeight()));

        batcher.draw(verticalMiddle, top, position.set(x + w/2f, y - corner.getHalfHeight()));
        batcher.draw(verticalMiddle, bottom, position.set(x + w/2f, y - h + corner.getHalfHeight()));
        batcher.draw(horizontalMiddle, left, position.set(x + corner.getHalfWidth(), y - h/2f));
        batcher.draw(horizontalMiddle, right, position.set(x + w - corner.getHalfWidth(), y - h/2f));

        batcher.draw(center, this.center, position.set(x + w/2f, y - h/2f));
    }

    public Texture getTexture() {
        return atlas.getTexture();
    }
}
