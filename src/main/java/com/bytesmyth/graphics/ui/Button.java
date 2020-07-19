package com.bytesmyth.graphics.ui;

import org.joml.Vector3f;

public class Button extends Node {

    public interface Listener {
        void onClicked();
    }

    private String text;

    private Vector3f color = new Vector3f(1,1,1);
    private Vector3f textColor = new Vector3f(0,0,0);

    private Listener listener;

    public Button(String text, float w, float h) {
        super(w, h);
        this.text = text;
    }

    public Button setListener(Listener listener) {
        this.listener = listener;
        return this;
    }

    public String getText() {
        return text;
    }

    public Vector3f getColor() {
        return color;
    }

    public Button setColor(Vector3f color) {
        this.color = color;
        return this;
    }

    public Vector3f getTextColor() {
        return textColor;
    }

    public Button setTextColor(Vector3f textColor) {
        this.textColor = textColor;
        return this;
    }

    void fireClick() {
        listener.onClicked();
    }
}
