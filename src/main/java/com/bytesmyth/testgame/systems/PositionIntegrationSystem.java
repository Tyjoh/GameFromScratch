package com.bytesmyth.testgame.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.testgame.components.Transform;
import com.bytesmyth.testgame.components.Velocity;
import org.joml.Vector2f;

public class PositionIntegrationSystem extends IteratingSystem {

    private ComponentMapper<Transform> mTransform;
    private ComponentMapper<Velocity> mVelocity;

    private Vector2f tempVelocity = new Vector2f();

    public PositionIntegrationSystem() {
        super(Aspect.all(Transform.class, Velocity.class));
    }

    @Override
    protected void process(int i) {
        Transform transform = mTransform.get(i);
        Velocity velocity = mVelocity.get(i);

        tempVelocity.set(velocity.getVelocity());
        tempVelocity.mul(world.delta);

        transform.translate(tempVelocity);
    }
}
