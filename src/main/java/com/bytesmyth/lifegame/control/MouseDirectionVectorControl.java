package com.bytesmyth.lifegame.control;

import com.bytesmyth.application.Input;
import com.bytesmyth.util.Provider;
import org.joml.Vector2f;

import java.util.function.Function;

public class MouseDirectionVectorControl implements VectorControl {

    private final Function<Vector2f, Vector2f> toWorldPosition;
    private final Provider<Vector2f> originProvider;

    private final Vector2f lookDirection = new Vector2f();

    public MouseDirectionVectorControl(Function<Vector2f, Vector2f> toWorldPosition, Provider<Vector2f> originProvider) {
        this.toWorldPosition = toWorldPosition;
        this.originProvider = originProvider;
    }

    @Override
    public void poll(Input input, long timestamp) {
        Vector2f worldMouse = toWorldPosition.apply(input.getMousePosition());
        Vector2f controlOrigin = originProvider.get();
        lookDirection.set(worldMouse).sub(controlOrigin);
    }

    @Override
    public void tick(float dt) {

    }

    @Override
    public Vector2f getValue() {
        return lookDirection;
    }
}
