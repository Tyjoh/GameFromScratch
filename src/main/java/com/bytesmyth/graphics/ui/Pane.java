package com.bytesmyth.graphics.ui;

import com.bytesmyth.graphics.batch.QuadTextureBatcher;
import com.bytesmyth.graphics.texture.NinePatch;
import org.joml.Vector2f;

public class Pane extends Container {

    private float opacity = 1f;

    public Pane() {
    }

    @Override
    public void draw(GuiGraphics g) {
        NinePatch windowPatch = g.getWindowPatch();
        QuadTextureBatcher batcher = g.getBatcher();

        Vector2f position = getGuiPosition();

        batcher.setColor(1,1,1, opacity);
        windowPatch.draw(position.x, position.y, getWidth(), getHeight(), batcher);
        batcher.setColor(1,1,1, 1);

        super.draw(g);
    }

    public float getOpacity() {
        return opacity;
    }

    public Pane setOpacity(float opacity) {
        this.opacity = opacity;
        return this;
    }
}
