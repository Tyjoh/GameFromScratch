package com.bytesmyth.graphics.ui;

import com.bytesmyth.graphics.Graphics;
import org.joml.Vector2f;

import java.util.*;

public class Container extends Node {

    private final List<Node> children = new ArrayList<>();

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
    public void draw(Graphics g) {
        for (Node child : children) {
            g.getBatcher().begin(this.getGui().getTheme().getTexture());
            child.draw(g);
        }
    }

    @Override
    void pollMouseEvents(Mouse mouse) {
        for (Node child : children) {
            child.pollMouseEvents(mouse);
        }
        super.pollMouseEvents(mouse);
    }

    public void addNode(Node node) {
        this.children.add(node);
        node.setParent(this);
        node.setGui(this.getGui());
    }

    public void removeNode(Node node) {
        this.children.remove(node);
        node.setParent(null);
        node.setGui(null);
    }

    public void clearNodes() {
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
