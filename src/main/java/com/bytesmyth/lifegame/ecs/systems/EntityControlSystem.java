package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.application.Input;
import com.bytesmyth.lifegame.LifeGame;
import com.bytesmyth.lifegame.ecs.components.*;
import org.joml.Vector2f;
import org.joml.Vector2i;

@All({UserControl.class, TransformComponent.class, VelocityComponent.class})
public class EntityControlSystem extends IteratingSystem {

    private ComponentMapper<UserControl> mUserControl;
    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<VelocityComponent> mVelocity;

    private ComponentMapper<LookDirectionComponent> mDirection;

    private final Vector2f controlDir = new Vector2f();
    private final Vector2i dir = new Vector2i();

    @Wire
    private LifeGame game;

    @Override
    protected void begin() {
        super.begin();
    }

    @Override
    protected void process(int entityId) {
        UserControl userControl = mUserControl.get(entityId);
        VelocityComponent velocityComponent = mVelocity.get(entityId);

        Input input = game.getInput();

        dir.zero();

        if (input.getKey("W").isPressed()) {
            dir.y = 1;
        } else if(input.getKey("S").isPressed()) {
            dir.y = -1;
        }

        if (input.getKey("A").isPressed()) {
            dir.x = -1;
        } else if (input.getKey("D").isPressed()) {
            dir.x = 1;
        }

        controlDir.set(dir);
        if (controlDir.lengthSquared() > 0) {
            controlDir.normalize();
        }

        velocityComponent.getVelocity().set(controlDir).mul(userControl.getControlSpeed());

        Vector2f position = mTransform.get(entityId).getPosition();
        Vector2f mouse = game.getWorldMousePosition();

        Vector2f lookDirection = new Vector2f(mouse).sub(position);
        if (mDirection.has(entityId)) {
            mDirection.get(entityId).setLookDir(lookDirection);
        }

    }
}
