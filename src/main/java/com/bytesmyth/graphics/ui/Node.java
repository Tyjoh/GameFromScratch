package com.bytesmyth.graphics.ui;

import com.bytesmyth.graphics.ui.positioning.DefaultPositioning;
import com.bytesmyth.graphics.ui.positioning.Positioning;
import org.joml.Vector2f;

public abstract class Node {

    private Gui gui;
    private Container parent;

    private String key;

    private float x = 0;
    private float y = 0;

    private float w = 10;//use reasonable default for easy error detection.
    private float h = 10;

    private boolean hovered = false;
    private boolean pressed = false;

    private Positioning positioning = new DefaultPositioning();

    protected Node() {
    }

    public void draw(GuiGraphics g) { }

    public void layout() { }

    public Node setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Node setSize(float width, float height) {
        this.w = width;
        this.h = height;
        return this;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public boolean isHovered() {
        return hovered;
    }

    Node setHovered(boolean hovered) {
        this.hovered = hovered;
        return this;
    }

    public boolean isPressed() {
        return pressed;
    }

    Node setPressed(boolean pressed) {
        this.pressed = pressed;
        return this;
    }

    Node setParent(Container parent) {
        this.parent = parent;
        return this;
    }

    Container getParent() {
        return parent;
    }

    Gui getGui() {
        return gui;
    }

    Node setGui(Gui gui) {
        this.gui = gui;
        if (gui != null) {
            this.gui.registerNode(this);
        }
        return this;
    }

    public Node setKey(String key) {
        this.key = key;
        if(gui != null) {
            gui.registerNode(this);
        }
        return this;
    }

    public void clearFlags() {
        this.setHovered(false);
        this.setPressed(false);
    }

    protected String getKey() {
        return key;
    }

    public Positioning getPositioning() {
        return positioning;
    }

    public Node setPositioning(Positioning positioning) {
        this.positioning = positioning;
        return this;
    }

    public Vector2f getRenderPosition() {
        Vector2f pos = new Vector2f(getX(), getY());

        if(getParent() != null) {
            pos.add(getParent().getRenderPosition());
        }

        return pos;
    }

    public Vector2f getPosition() {
        return new Vector2f(x, y);
    }
}
