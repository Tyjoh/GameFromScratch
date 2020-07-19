package com.bytesmyth.ui;

public class Position {
    private float x;
    private float y;
    private float width;
    private float height;

    public Position(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getHalfWidth() {
        return width/2f;
    }

    public float getHalfHeight() {
        return height / 2f;
    }
}
