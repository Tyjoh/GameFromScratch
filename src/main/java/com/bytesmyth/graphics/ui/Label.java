package com.bytesmyth.graphics.ui;

import com.bytesmyth.graphics.font.BitmapFont;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Label extends Node {

    private String text;
    private Vector3f color;
    private float fontSize = 14f;

    public Label(String text) {
        this.text = text;
    }

    @Override
    public void draw(GuiGraphics g) {
        BitmapFont font = g.getFont();

        Vector2f size = font.getTextSize(text, fontSize);
        this.setSize(size.x, size.y);

        Vector2f renderPosition = getGuiPosition();

        font.drawText(text, renderPosition.x + getWidth()/2f - size.x/2f, renderPosition.y - getHeight()/2f + size.y / 2f, fontSize, g.getBatcher());
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

    public float getFontSize() {
        return fontSize;
    }

    public Label setFontSize(float fontSize) {
        this.fontSize = fontSize;
        return this;
    }
}
