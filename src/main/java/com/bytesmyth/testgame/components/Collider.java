package com.bytesmyth.testgame.components;

import com.artemis.Component;
import com.bytesmyth.graphics.mesh.Rectangle;
import org.joml.Vector2f;

public class Collider extends Component {

    private Rectangle hitBox = new Rectangle(0,0);
    private Vector2f offset = new Vector2f();

    public Collider setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
        return this;
    }

    public Collider setOffset(Vector2f offset) {
        this.offset = offset;
        return this;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public Vector2f getOffset() {
        return offset;
    }
}
