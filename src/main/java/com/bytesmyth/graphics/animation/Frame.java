package com.bytesmyth.graphics.animation;

import com.bytesmyth.graphics.sprite.Sprite;

public class Frame {
    private final Sprite sprite;
    private final int frameTicks;

    public Frame(Sprite sprite, int frameTicks) {
        this.sprite = sprite;
        this.frameTicks = frameTicks;
    }

    public Frame(Sprite sprite) {
        this(sprite, 7);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getFrameTicks() {
        return frameTicks;
    }
}
