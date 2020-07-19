package com.bytesmyth.ui;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Label extends Node {

    private String text;
    private Vector3f color;

    public Label(String text) {
        super(0, 0);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Label setText(String text) {
        this.text = text;
        return this;
    }

    public Vector3f getColor() {
        return color;
    }

    public Label setColor(Vector3f color) {
        this.color = color;
        return this;
    }

    @Override
    public float getWidth() {
        Vector2f textSize = getGui().getFont().getTextSize(text, 16);
        return textSize.x;
    }

    @Override
    public float getHeight() {
        Vector2f textSize = getGui().getFont().getTextSize(text, 16);
        return textSize.y;
    }
}
