package com.bytesmyth.graphics.ui;

import java.util.*;

public class Container extends Node {

    private List<Node> children = new ArrayList<>();

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