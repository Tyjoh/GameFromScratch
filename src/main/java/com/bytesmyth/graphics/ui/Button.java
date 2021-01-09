package com.bytesmyth.graphics.ui;

import com.bytesmyth.graphics.batch.QuadTextureBatcher;
import com.bytesmyth.graphics.texture.NinePatch;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Button extends Node {

    private String text;

    private Vector3f color = new Vector3f(1,1,1);
    private Vector3f textColor = new Vector3f(0,0,0);

    public Button(String text) {
        this.text = text;
    }

    @Override
    public void draw(GuiGraphics g) {
        Vector3f color = new Vector3f(this.color);

        if (isPressed()) {
            color.mul(0.65f);
        } else if (isHovered()) {
            color.mul(0.85f);
        }

        QuadTextureBatcher batcher = g.getBatcher();
        NinePatch buttonPatch = g.getButtonPatch();

        batcher.setColor(color.x, color.y, color.z, 1f);
        buttonPatch.draw(getX(), getY(), getWidth(), getHeight(), batcher);

        int buttonFontSize = 16;
        Vector2f size = g.getFont().getTextSize(text, buttonFontSize);

        batcher.setColor(textColor.x, textColor.y, textColor.z, 1f);
        g.getFont().drawText(text,getX() + getWidth()/2f - size.x/2f, getY() - getHeight()/2f + size.y / 2f, buttonFontSize, batcher);

        batcher.setColor(1,1,1,1);
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

}
