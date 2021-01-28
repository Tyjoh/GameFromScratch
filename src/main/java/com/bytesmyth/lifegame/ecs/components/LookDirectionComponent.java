package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Component;
import org.joml.Vector2f;

public class LookDirectionComponent extends Component {

    public static final Vector2f ZERO = new Vector2f(1, 0);
    private final Vector2f lookDir = new Vector2f();

    public Vector2f getLookDir() {
        return lookDir;
    }

    public void setLookDir(Vector2f dir) {
        this.lookDir.set(dir);
        if (this.lookDir.lengthSquared() > 0) {
            this.lookDir.normalize();
        }
    }

    public float getLookAngleRad() {
        return lookDir.angle(ZERO);
    }

    public boolean lookingUp() {
        return lookDir.y > 0;
    }

    public boolean lookingDown() {
        return lookDir.y < 0;
    }

    public boolean lookingLeft() {
        return lookDir.x < 0;
    }

    public boolean lookingRight() {
        return lookDir.x > 0;
    }
}
