package com.bytesmyth.graphics.ui;

import org.joml.Vector2f;

import java.util.*;

public class Container extends Node {

    private List<Node> children = new ArrayList<>();

    @Override
    public void layout() {
        for (Node child : children) {
            Vector2f size = new Vector2f(getWidth(), getHeight());
            Vector2f position = child.getPositioning().position(size, child);
            child.setPosition(position.x, position.y);

            child.layout();
        }
    }

    @Override
    public void draw(GuiGraphics g) {
        for (Node child : children) {
            child.draw(g);
        }
    }

    public void addChild(Node node) {
        this.children.add(node);
        node.setParent(this);
        node.setGui(this.getGui());
    }

    public void removeChild(Node node) {
        this.children.remove(node);
        node.setParent(null);
        node.setGui(null);
    }

    public void clearChildren() {
        this.children.clear();
    }

    public List<Node> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public Container setGui(Gui gui) {
        super.setGui(gui);
        for (Node child : this.children) {
            child.setGui(gui);
        }
        return this;
    }
}
