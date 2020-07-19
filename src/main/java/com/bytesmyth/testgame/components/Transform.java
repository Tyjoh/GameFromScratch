package com.bytesmyth.testgame.components;

import com.artemis.Component;
import org.joml.Vector2f;


public class Transform extends Component {

    private Vector2f position = new Vector2f();

    public void translate(Vector2f delta) {
        this.position.add(delta);
    }

    public Transform setPosition(Vector2f position) {
        this.position = position;
        return this;
    }

    public Vector2f getPosition() {
        return position;
    }
}
