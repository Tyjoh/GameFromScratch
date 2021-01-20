package com.bytesmyth.graphics.sprite;

import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureRegion;
import org.joml.Vector2f;

public class StaticSprite implements Sprite {

    private final Texture texture;
    private final TextureRegion region;

    private float width = 1;
    private float height = 1;

    private Vector2f origin = new Vector2f();

    public StaticSprite(Texture texture, TextureRegion region) {
        this.texture = texture;
        this.region = region;
    }

    public StaticSprite setSize(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public StaticSprite setOrigin(Vector2f origin) {
        this.origin.set(origin);
        return this;
    }

    public StaticSprite setOrigin(float x, float y) {
        this.origin.set(x, y);
        return this;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public TextureRegion getTextureRegion() {
        return region;
    }

    @Override
    public Vector2f getOrigin() {
        return origin;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

}
