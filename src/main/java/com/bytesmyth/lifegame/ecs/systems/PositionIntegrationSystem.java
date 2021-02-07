package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.lifegame.ecs.components.TransformComponent;
import com.bytesmyth.lifegame.ecs.components.VelocityComponent;
import com.bytesmyth.lifegame.tilemap.Chunk;
import com.bytesmyth.lifegame.tilemap.TileMap;
import org.joml.Vector2f;

public class PositionIntegrationSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<VelocityComponent> mVelocity;

    private Vector2f tempVelocity = new Vector2f();

    private TileMap map;

    public PositionIntegrationSystem() {
        super(Aspect.all(TransformComponent.class, VelocityComponent.class));
    }

    @Override
    protected void process(int entityId) {
        TransformComponent transformComponent = mTransform.get(entityId);
        VelocityComponent velocityComponent = mVelocity.get(entityId);

        tempVelocity.set(velocityComponent.getVelocity());
        tempVelocity.mul(world.delta);

        transformComponent.translate(tempVelocity);
    }
}
