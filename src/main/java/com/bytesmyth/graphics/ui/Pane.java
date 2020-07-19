package com.bytesmyth.graphics.ui;

import java.util.*;

public class Pane extends Node {

    private List<Node> children = new ArrayList<>();
    private Map<Node, RelativePositioning> positioning = new HashMap<>();

    public Pane(float w, float h) {
        super(w, h);
    }

    public void addChild(Node node, RelativePositioning basicPositioning) {
        this.children.add(node);
        this.positioning.put(node, basicPositioning);
        node.setParent(this);
        node.setGui(this.getGui());
    }

    public List<Node> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public RelativePositioning getNodePositioning(Node node) {
        return positioning.get(node);
    }
}
