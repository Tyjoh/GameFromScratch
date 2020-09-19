package com.bytesmyth.ui;

public abstract class Node {

    private Gui gui;
    private Container parent;

    private String key;

    private float w;
    private float h;

    private boolean focused = false;
    private boolean hovered = false;
    private boolean pressed = false;
    private boolean dragged = false;

    private boolean draggable = false;

    private Positioning positioning = RelativePositioning.center();

    protected Node(float w, float h) {
        this.w = w;
        this.h = h;
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
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

    public Node setGui(Gui gui) {
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
        this.setDragged(false);
        this.setFocused(false);
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

    public boolean isDraggable() {
        return draggable;
    }

    public Node setDraggable(boolean draggable) {
        this.draggable = draggable;
        return this;
    }
}
