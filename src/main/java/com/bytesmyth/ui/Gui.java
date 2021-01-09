package com.bytesmyth.ui;

import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.input.Input;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Gui extends Container {

    private Map<String, Node> keyNodeMap = new HashMap<>();

    @Override
    public void draw(GuiGraphics g) {
        OrthographicCamera2D camera = g.getCamera();
        layout(0, 0, camera.getWidth(), camera.getHeight());

        for (Node node : getRenderOrder()) {
            node.draw(g);
        }
    }

    public void handleInput(Input input) {

    }

    private List<Node> getRenderOrder() {
        List<Node> nodes = new LinkedList<>();

        for (Node node : getChildren()) {
            order(node, nodes);
        }

        return nodes;
    }

    private void order(Node node, List<Node> output) {
        output.add(node);

        if (node instanceof Container) {
            for (Node child : ((Container) node).getChildren()) {
                order(child, output);
            }
        }
    }

    void layout(float x, float y, float width, float height) {
        this.setPosition(x, y);
        this.setSize(width, height);

        Position rootPosition = new Position(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        position(this, rootPosition);
    }

    private void position(Node node, Position position) {
        float hw = node.getWidth() / 2f;
        float hh = node.getHeight() / 2f;

        node.setPosition(position.getX() - hw, position.getY() + hh);

        if (node instanceof Container) {
            Container container = (Container) node;
            for (Node child : container.getChildren()) {
                Position childPosition = child.getPositioning().position(position, child, this);
                position(child, childPosition);
            }
        }
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
