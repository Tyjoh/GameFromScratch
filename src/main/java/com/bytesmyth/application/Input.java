package com.bytesmyth.application;

import org.joml.Vector2f;

public interface Input {
    Button getKey(String key);

    Vector2f getMousePosition();

    Button getLeftMouseButton();

    Button getRightMouseButton();
}
