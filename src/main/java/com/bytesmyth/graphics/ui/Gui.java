package com.bytesmyth.graphics.ui;

import com.bytesmyth.application.Input;

import java.util.HashMap;
import java.util.Map;

public abstract class Gui extends Container {

    private final Map<String, Node> keyNodeMap = new HashMap<>();

    public void handleInput(Input input) {

    }

    void layout(float x, float y, float width, float height) {
        this.setPosition(x, y);
        this.setSize(width, height);
        layout();
    }

    @Override
    Gui getGui() {
        return this;
    }

    public void registerNode(Node node) {
        if (node.getKey() != null) {
            this.keyNodeMap.put(node.getKey(), node);
        }
    }

    public Node getNode(String key) {
        return keyNodeMap.get(key);
    }

    public boolean hasNode(String key) {
        return keyNodeMap.containsKey(key);
    }
}
