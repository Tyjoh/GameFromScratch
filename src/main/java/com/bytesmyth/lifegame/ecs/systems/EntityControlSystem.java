package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.application.GameContext;
import com.bytesmyth.application.Input;
import com.bytesmyth.lifegame.ecs.components.Direction;
import com.bytesmyth.lifegame.ecs.components.UserControl;
import com.bytesmyth.lifegame.ecs.components.Transform;
import com.bytesmyth.lifegame.ecs.components.Velocity;
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
    private GameContext gameContext;

    @Override
    protected void begin() {
        super.begin();
    }

    @Override
    protected void process(int i) {
        UserControl userControl = mEntityControl.get(i);
        Velocity velocity = mVelocity.get(i);

        Input input = gameContext.getInput();

        dir.zero();

        if (input.getKey("A").isPressed()) {
            dir.x = -1;
        } else if (input.getKey("D").isPressed()) {
            dir.x = 1;
        }

        if (input.getKey("W").isPressed()) {
            dir.y = 1;
        } else if(input.getKey("S").isPressed()) {
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
