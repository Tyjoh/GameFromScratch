package com.bytesmyth.graphics.sprite;

import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureRegion;
import org.joml.Vector2f;

public class Sprite {
    private Texture texture;
    private TextureRegion region;

    private float width = 1;
    private float height = 1;
    private float rotation = 0;

    private final Vector2f origin = new Vector2f();

    private boolean flipX = false;
    private boolean flipY = false;

    public Sprite(Texture texture, TextureRegion region) {
        this.texture = texture;
        this.region = region;
    }

    public Sprite setSize(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Sprite setOrigin(Vector2f origin) {
        this.origin.set(origin);
        return this;
    }

    public Sprite setOrigin(float x, float y) {
        this.origin.set(x, y);
        return this;
    }

    public Sprite setRotation(float rotation) {
        this.rotation = rotation;
        return this;
    }

    public Sprite setRegion(TextureRegion region) {
        this.region = region;
        if (flipX) this.region = this.region.getFlippedX();
        if (flipY) this.region = this.region.getFlippedY();
        return this;
    }

    public Texture getTexture() {
        return texture;
    }

    public TextureRegion getTextureRegion() {
        return region;
    }

    public Vector2f getOrigin() {
        return origin;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

    public Sprite setFlipX(boolean flipX) {
        if (flipX != this.flipX) {
            this.region = region.getFlippedX();
            this.flipX = flipX;
        }
        return this;
    }

    public Sprite setFlipY(boolean flipY) {
        if (flipY != this.flipY) {
            this.region = region.getFlippedY();
            this.flipY = flipY;
        }
        return this;
    }

}
