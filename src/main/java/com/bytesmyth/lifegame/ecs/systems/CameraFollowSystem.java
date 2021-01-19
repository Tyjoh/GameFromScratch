package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.lifegame.ecs.components.CameraFollowComponent;
import com.bytesmyth.lifegame.ecs.components.TransformComponent;
import org.joml.Vector2f;

public class CameraFollowSystem extends IteratingSystem {

    private ComponentMapper<CameraFollowComponent> mCameraFollow;
    private ComponentMapper<TransformComponent> mTransform;

    @Wire
    private OrthographicCamera2D camera;

    private Vector2f delta = new Vector2f();

    public CameraFollowSystem() {
        super(Aspect.all(CameraFollowComponent.class, TransformComponent.class));
    }

    @Override
    protected void process(int entityId) {
        CameraFollowComponent cameraFollowComponent = mCameraFollow.get(entityId);
        TransformComponent transformComponent = mTransform.get(entityId);
        camera.writePrevTransform();

        delta.set(transformComponent.getPosition()).sub(camera.getPosition());

        if (delta.length() >= cameraFollowComponent.getMinDelta()) {
            camera.move(delta.mul(cameraFollowComponent.getTween() * world.delta));
        }
    }
}
