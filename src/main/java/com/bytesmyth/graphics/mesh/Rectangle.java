package com.bytesmyth.graphics.mesh;

import org.joml.Vector2f;

public class Rectangle {

    private Vector2f topLeft;
    private Vector2f bottomRight;

    private float width;
    private float height;

    public Rectangle(float width, float height) {
        this.width = width;
        this.height = height;
        this.topLeft = new Vector2f(-width/2f, height/2f);
        this.bottomRight = new Vector2f(width/2f, -height/2f);
    }

    public Vector2f getTopLeft() {
        return topLeft;
    }

    public Vector2f getBottomRight() {
        return bottomRight;
    }

    public float getHalfWidth() {
        return width * 0.5f;
    }

    public float getHalfHeight() {
        return height * 0.5f;
    }
}
