package com.bytesmyth.input;

import org.joml.Vector2f;

public interface Input {

    boolean isKeyDown(String key);

    boolean isMouseDown(String button);

    Vector2f getMousePosition();

}
