package com.bytesmyth.testgame.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.input.InputWrapper;
import com.bytesmyth.testgame.components.Direction;
import com.bytesmyth.testgame.components.UserControl;
import com.bytesmyth.testgame.components.Transform;
import com.bytesmyth.testgame.components.Velocity;
import org.joml.Vector2f;
import org.joml.Vector2i;

@All({UserControl.class, Transform.class, Velocity.class})
public class EntityControlSystem extends IteratingSystem {

    private ComponentMapper<UserControl> mEntityControl;
    private ComponentMapper<Velocity> mVelocity;
    private ComponentMapper<Direction> mDirection;

    private Vector2f controlDir = new Vector2f();
    private Vector2i dir = new Vector2i();

    @Wire
    private InputWrapper input;

    @Override
    protected void begin() {
        super.begin();
    }

    @Override
    protected void process(int i) {
        UserControl userControl = mEntityControl.get(i);
        Velocity velocity = mVelocity.get(i);

        dir.zero();

        if (input.isKeyDown("A")) {
            dir.x = -1;
        } else if (input.isKeyDown("D")) {
            dir.x = 1;
        }

        if (input.isKeyDown("W")) {
            dir.y = 1;
        } else if(input.isKeyDown("S")) {
            dir.y = -1;
        }

        controlDir.set(dir);
        if (controlDir.lengthSquared() > 0) {
            controlDir.normalize();
        }

        velocity.getVelocity().set(controlDir).mul(userControl.getControlSpeed());

        if (dir.lengthSquared() > 0 && mDirection.has(i)) {
            mDirection.get(i).setDir(dir);
        }
    }
}
