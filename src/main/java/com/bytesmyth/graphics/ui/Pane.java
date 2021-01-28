package com.bytesmyth.graphics.ui;

import com.bytesmyth.graphics.sprite.SpriteBatcher;
import com.bytesmyth.graphics.texture.NinePatch;
import com.bytesmyth.lifegame.Graphics;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Pane extends Container {

    private float opacity = 1f;
    private Vector3f color = new Vector3f(1,1,1);

    public Pane() {
    }

    @Override
    public void draw(Graphics g) {
        NinePatch window = getGui().getTheme().getWindow();
        SpriteBatcher batcher = g.getBatcher();

        Vector2f position = getGuiPosition();

        batcher.setColor(color.x, color.y, color.z, opacity);
        window.draw(position.x, position.y, getWidth(), getHeight(), batcher);
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

    public Vector3f getColor() {
        return color;
    }

    public Pane setColor(Vector3f color) {
        this.color = color;
        return this;
    }
}
