package com.bytesmyth.graphics.ui;

import com.bytesmyth.application.Input;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.graphics.Graphics;

import java.util.HashMap;
import java.util.Map;

public abstract class Gui extends Container {

    private final Map<String, Node> keyNodeMap = new HashMap<>();

    private final Mouse mouse = new Mouse();
    private boolean enabled = false;

    private GuiTheme theme = new GuiTheme();
    private Graphics graphics;

    public void pollInput(Input input, OrthographicCamera2D camera) {
        mouse.updateMouseState(input, camera);
        this.pollMouseEvents(mouse);
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    public GuiTheme getTheme() {
        return theme;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

        if (mouse.isHoldingNode()) {
            Node heldNode = mouse.getHeldNode();
            heldNode.setPosition(mouse.getPosition().x - heldNode.getWidth()/2f, mouse.getPosition().y + heldNode.getHeight()/2f);
            heldNode.draw(g);
        }
    }

    @Override
    public Gui getGui() {
        return this;
    }

    public Mouse getMouse() {
        return mouse;
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
