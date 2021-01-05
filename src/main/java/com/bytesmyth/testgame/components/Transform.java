package com.bytesmyth.testgame.components;

import com.artemis.Component;
import org.joml.Vector2f;


public class Transform extends Component {

    private Vector2f position = new Vector2f();
    private Vector2f prevPosition = new Vector2f();

    public void translate(Vector2f delta) {
        this.position.add(delta);
    }

    public Transform setPosition(Vector2f position) {
        this.position.set(position);
        return this;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void writePrevious() {
        this.prevPosition.set(position);
    }

    public Vector2f getPrevPosition() {
        return prevPosition;
    }
}
