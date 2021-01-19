package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.lifegame.ecs.components.TransformComponent;
import com.bytesmyth.lifegame.ecs.components.VelocityComponent;
import org.joml.Vector2f;

public class PositionIntegrationSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<VelocityComponent> mVelocity;

    private Vector2f tempVelocity = new Vector2f();

    public PositionIntegrationSystem() {
        super(Aspect.all(TransformComponent.class, VelocityComponent.class));
    }

    @Override
    protected void process(int i) {
        TransformComponent transformComponent = mTransform.get(i);
        VelocityComponent velocityComponent = mVelocity.get(i);

        tempVelocity.set(velocityComponent.getVelocity());
        tempVelocity.mul(world.delta);

        transformComponent.translate(tempVelocity);
    }
}
