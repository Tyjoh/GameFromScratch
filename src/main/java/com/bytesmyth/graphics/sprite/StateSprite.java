package com.bytesmyth.graphics.sprite;

import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureRegion;
import org.joml.Vector2f;

public class StateSprite implements Sprite {

    private Texture texture;
    private TextureRegion[] states;

    private int state = 0;

    private float width = 1;
    private float height = 1;

    private Vector2f origin = new Vector2f();


    public StateSprite(Texture texture, TextureRegion[] states) {
        this.texture = texture;
        this.states = states;
    }

    public StateSprite setSize(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public StateSprite setOrigin(Vector2f origin) {
        this.origin.set(origin);
        return this;
    }

    public StateSprite setOrigin(float x, float y) {
        this.origin.set(x, y);
        return this;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public TextureRegion getTextureRegion() {
        return states[state];
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

    public void setState(int state) {
        this.state = state;
    }

    public void nextState() {
        state = (state + 1) % states.length;
    }

    public int getState() {
        return state;
    }

}
