package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Component;
import org.joml.Vector2f;

public class Velocity extends Component {

    private Vector2f velocity = new Vector2f();

    public void add(Vector2f deltaVelocity) {
        this.velocity.add(deltaVelocity);
    }

    public Vector2f getVelocity() {
        return velocity;
    }
}
