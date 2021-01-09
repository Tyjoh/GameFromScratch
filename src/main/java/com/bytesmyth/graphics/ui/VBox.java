package com.bytesmyth.graphics.ui;

public class VBox extends Container {

    private float spacing;

    @Override
    public void layout() {
        float y = 0;
        for (Node child : getChildren()) {
            float x = child.getX();
            child.setPosition(x, y);
            y -= child.getHeight() + spacing;
        }
    }

    public float getSpacing() {
        return spacing;
    }

    public VBox setSpacing(float spacing) {
        this.spacing = spacing;
        return this;
    }
}
