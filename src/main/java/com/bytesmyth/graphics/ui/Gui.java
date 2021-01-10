package com.bytesmyth.graphics.ui;

import com.bytesmyth.application.Input;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;

import java.util.HashMap;
import java.util.Map;

public abstract class Gui extends Container {

    private final Map<String, Node> keyNodeMap = new HashMap<>();

    private final Mouse mouse = new Mouse();

    public void pollInput(Input input, OrthographicCamera2D camera) {
        mouse.updateMouseState(input, camera);
        this.pollMouseEvents(mouse);
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
