package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Component;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector2ic;

public class Direction extends Component {

    private Vector2f dir = new Vector2f();

    public Vector2f getDir() {
        return dir;
    }

    public void setDir(Vector2i dir) {
        if (dir.lengthSquared() == 0) {
            throw new IllegalArgumentException("Invalid direction " + dir);
        }

        this.dir.zero();

        if (dir.x > 0) {
            this.dir.x = 1;
        } else if (dir.x < 0) {
            this.dir.x = -1;
        }

        if (dir.y > 0) {
            this.dir.y = 1;
        } else if (dir.y < 0) {
            this.dir.y = -1;
        }
    }
}
