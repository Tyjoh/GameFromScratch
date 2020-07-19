package com.bytesmyth.testgame.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.input.InputWrapper;
import com.bytesmyth.testgame.components.InputControl;
import com.bytesmyth.testgame.components.Transform;
import com.bytesmyth.testgame.components.Velocity;
import org.joml.Vector2f;

@All({InputControl.class, Transform.class, Velocity.class})
public class EntityControlSystem extends IteratingSystem {

    private ComponentMapper<InputControl> mEntityControl;
    private ComponentMapper<Velocity> mVelocity;

    private Vector2f controlDir = new Vector2f();

    @Wire
    private InputWrapper input;

    @Override
    protected void begin() {
        super.begin();
        controlDir.zero();

        if (input.isKeyDown("A")) {
            controlDir.x = -1;
        } else if (input.isKeyDown("D")) {
            controlDir.x = 1;
        }

        if (input.isKeyDown("W")) {
            controlDir.y = 1;
        } else if(input.isKeyDown("S")) {
            controlDir.y = -1;
        }

        if (controlDir.length() > 0) {
            controlDir.normalize();
        }
    }

    @Override
    protected void process(int i) {
        InputControl inputControl = mEntityControl.get(i);
        Velocity velocity = mVelocity.get(i);

        velocity.getVelocity().set(controlDir).mul(inputControl.getControlSpeed());
    }
}
