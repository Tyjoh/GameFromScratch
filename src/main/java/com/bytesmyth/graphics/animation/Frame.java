package com.bytesmyth.graphics.animation;

import com.bytesmyth.graphics.texture.TextureRegion;

public class Frame {
    private TextureRegion region;
    private int frameTicks = 7;

    public Frame(TextureRegion region, int frameTicks) {
        this.region = region;
        this.frameTicks = frameTicks;
    }

    public Frame(TextureRegion region) {
        this.region = region;
    }

    public TextureRegion getRegion() {
        return region;
    }

    public int getFrameTicks() {
        return frameTicks;
    }
}
