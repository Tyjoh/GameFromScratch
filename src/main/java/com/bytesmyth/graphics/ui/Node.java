package com.bytesmyth.graphics.ui;

public abstract class Node {

    private Gui gui;
    private Node parent;

    private float w;
    private float h;

    private boolean focused;
    private boolean hovered;
    private boolean pressed;
    private boolean dragged;

    protected Node(float w, float h) {
        this.w = w;
        this.h = h;
    }

    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }

    public Node setW(float w) {
        this.w = w;
        return this;
    }

    public Node setH(float h) {
        this.h = h;
        return this;
    }

    public boolean isFocused() {
        return focused;
    }

    Node setFocused(boolean focused) {
        this.focused = focused;
        return this;
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

    public boolean isDragged() {
        return dragged;
    }

    Node setDragged(boolean dragged) {
        this.dragged = dragged;
        return this;
    }

    Node setGui(Gui gui) {
        this.gui = gui;
        return this;
    }

    Node setParent(Node parent) {
        this.parent = parent;
        return this;
    }

    public Gui getGui() {
        return gui;
    }
}
