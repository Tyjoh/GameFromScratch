package com.bytesmyth.ui;

import java.util.*;

public class Container extends Node {

    private List<Node> children = new ArrayList<>();
    private Map<Node, Positioning> positioning = new HashMap<>();

    public Container(float w, float h) {
        super(w, h);
    }

    public void addChild(Node node, Positioning basicPositioning) {
        this.children.add(node);
        this.positioning.put(node, basicPositioning);
        node.setParent(this);
        node.setGui(this.getGui());
    }

    public List<Node> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public Positioning getNodePositioning(Node node) {
        return positioning.get(node);
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
