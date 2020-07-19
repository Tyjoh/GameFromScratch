package com.bytesmyth.graphics.texture;

public class TextureRegion {

    private float u1;
    private float v1;
    private float u2;
    private float v2;

    public TextureRegion(float u1, float v1, float u2, float v2) {
        this.u1 = u1;
        this.v1 = v1;
        this.u2 = u2;
        this.v2 = v2;
    }

    public float getU1() {
        return u1;
    }

    public float getV1() {
        return v1;
    }

    public float getU2() {
        return u2;
    }

    public float getV2() {
        return v2;
    }
}
