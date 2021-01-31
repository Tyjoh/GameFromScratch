package com.bytesmyth.lifegame.control;

import com.bytesmyth.application.Input;
import org.joml.Vector2f;

public class KeyboardVectorControl implements VectorControl {

    private final String upKey;
    private final String downKey;
    private final String leftKey;
    private final String rightKey;

    boolean up;
    boolean down;
    boolean left;
    boolean right;

    private Vector2f movementDir = new Vector2f();

    public KeyboardVectorControl(String upKey, String downKey, String leftKey, String rightKey) {
        this.upKey = upKey;
        this.downKey = downKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
    }

    public KeyboardVectorControl() {
        this("W", "S", "A", "D");
    }

    @Override
    public void poll(Input input, long t) {
        movementDir.zero();

        if (input.getKey(upKey).isPressed()) {
            up = true;
        }

        if (input.getKey(leftKey).isPressed()) {
            left = true;
        }

        if (input.getKey(downKey).isPressed()) {
            down = true;
        }

        if (input.getKey(rightKey).isPressed()) {
            right = true;
        }
    }

    @Override
    public void tick(float dt) {
        movementDir.zero();

        if (up) {
            movementDir.add(0, 1);
        }

        if (left) {
            movementDir.add(-1, 0);
        }

        if (down) {
            movementDir.add(0, -1);
        }

        if (right) {
            movementDir.add(1, 0);
        }

        up = false;
        down = false;
        left = false;
        right = false;

        if (movementDir.lengthSquared() > 0)
            movementDir.normalize();
    }

    @Override
    public Vector2f getValue() {
        return movementDir;
    }
}
