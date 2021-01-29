package com.bytesmyth.graphics;

import com.bytesmyth.graphics.sprite.SpriteBatcher;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureRegion;
import org.joml.Vector2f;

public class StaticTextureQuadGraphicsElement extends TexturedGraphicsElement {

    private int layer;

    private final Vector2f position = new Vector2f();
    private float width;
    private float height;

    private Texture texture;
    private TextureRegion region;

    public void set(Texture texture, TextureRegion region, float x, float y, float width, float height, int layer) {
        this.texture = texture;
        this.region = region;
        this.position.set(x, y);
        this.width = width;
        this.height = height;
        this.layer = layer;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public void draw(SpriteBatcher batcher, float alpha) {
        batcher.begin(texture);
        batcher.draw(position.x, position.y, position.x + width, position.y + height, region);
    }
}
