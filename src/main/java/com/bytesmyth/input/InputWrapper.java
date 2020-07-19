package com.bytesmyth.input;

import org.joml.Vector2f;

public class InputWrapper implements Input {
    public final Input input;

    public InputWrapper(Input input) {
        this.input = input;
    }

    @Override
    public boolean isKeyDown(String key) {
        return input.isKeyDown(key);
    }

    @Override
    public boolean isMouseDown(String button) {
        return input.isMouseDown(button);
    }

    @Override
    public Vector2f getMousePosition() {
        return input.getMousePosition();
    }
}
