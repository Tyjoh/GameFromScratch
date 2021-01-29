package com.bytesmyth.graphics.ui;

import com.bytesmyth.graphics.sprite.SpriteBatcher;
import com.bytesmyth.graphics.texture.NinePatch;
import com.bytesmyth.graphics.Graphics;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Button extends Node {

    private String text;
    private float fontSize = 12f;

    private Vector3f color = new Vector3f(1,1,1);
    private Vector3f textColor = new Vector3f(0,0,0);

    public Button(String text) {
        this.text = text;
    }

    @Override
    public void draw(Graphics g) {
        Vector3f color = new Vector3f(this.color);

        if (isPressed()) {
            color.mul(0.65f);
        } else if (isHovered()) {
            color.mul(0.85f);
        }

        SpriteBatcher batcher = g.getBatcher();
        GuiTheme theme = this.getGui().getTheme();

        NinePatch button = theme.getButton();
        Vector2f position = getGuiPosition();
        batcher.setColor(color.x, color.y, color.z, 1f);
        button.draw(position.x, position.y, getWidth(), getHeight(), batcher);

        Vector2f textSize = theme.getFont().getTextSize(text, fontSize);
        float textX = position.x + getWidth()/2f - textSize.x/2f;
        float textY = position.y - getHeight()/2f + textSize.y / 2f;

        batcher.setColor(textColor.x, textColor.y, textColor.z, 1f);
        theme.getFont().drawText(text, textX, textY, fontSize, batcher);
        batcher.setColor(1,1,1,1);
    }

    public String getText() {
        return text;
    }

    public Button setText(String text) {
        this.text = text;
        return this;
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

    public float getFontSize() {
        return fontSize;
    }

    public Button setFontSize(float fontSize) {
        this.fontSize = fontSize;
        return this;
    }
}
