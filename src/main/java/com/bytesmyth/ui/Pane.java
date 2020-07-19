package com.bytesmyth.ui;

public class Pane extends Container {

    private float opacity = 1f;

    public Pane(float w, float h) {
        super(w, h);
    }

    public float getOpacity() {
        return opacity;
    }

    public Pane setOpacity(float opacity) {
        this.opacity = opacity;
        return this;
    }
}
