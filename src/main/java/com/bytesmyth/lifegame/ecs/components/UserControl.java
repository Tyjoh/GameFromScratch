package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Component;

public class UserControl extends Component {
    private float controlSpeed = 6f;

    public float getControlSpeed() {
        return controlSpeed;
    }
}
