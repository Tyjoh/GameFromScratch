package com.bytesmyth.ui;

import com.bytesmyth.graphics.batch.QuadTextureBatcher;
import com.bytesmyth.graphics.texture.NinePatch;

public class Pane extends Container {

    private float opacity = 1f;

    public Pane() {
    }

    @Override
    public void draw(GuiGraphics g) {
        NinePatch windowPatch = g.getWindowPatch();
        QuadTextureBatcher batcher = g.getBatcher();

        batcher.setColor(1,1,1, opacity);
        windowPatch.draw(getX(), getY(), getWidth(), getHeight(), batcher);
        batcher.setColor(1,1,1, 1);
    }

    public float getOpacity() {
        return opacity;
    }

    public Pane setOpacity(float opacity) {
        this.opacity = opacity;
        return this;
    }
}
