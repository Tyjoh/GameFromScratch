package com.bytesmyth.graphics.ui;

import com.bytesmyth.application.Button;
import com.bytesmyth.application.Input;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import org.joml.Vector2f;

public class Mouse {

    private Vector2f position = new Vector2f();
    private Button lmb;
    private Button rmb;

    private Node heldNode;

    public Button getLeftButton() {
        return lmb;
    }

    public Button getRightButton() {
        return rmb;
    }

    public Vector2f getPosition() {
        return new Vector2f(position);
    }

    public void setHeldNode(Node node) {
        this.heldNode = node;
    }

    public boolean isHoldingNode() {
        return this.heldNode != null;
    }

    public Node getHeldNode() {
        return heldNode;
    }

    void updateMouseState(Input input, OrthographicCamera2D camera) {
        this.position.set(camera.toCameraCoordinates(input.getMousePosition()));
        this.lmb = input.getLeftMouseButton();
        this.rmb = input.getRightMouseButton();
    }
}
