package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Component;

public class CameraFollow extends Component {

    private float tween = 3f;
    private float minDelta = 0.01f;

    public float getTween() {
        return tween;
    }

    public float getMinDelta() {
        return minDelta;
    }
}
