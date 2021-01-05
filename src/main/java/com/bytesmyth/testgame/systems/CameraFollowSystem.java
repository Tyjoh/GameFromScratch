package com.bytesmyth.testgame.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.testgame.components.CameraFollow;
import com.bytesmyth.testgame.components.Transform;
import org.joml.Vector2f;

public class CameraFollowSystem extends IteratingSystem {

    private ComponentMapper<CameraFollow> mCameraFollow;
    private ComponentMapper<Transform> mTransform;

    @Wire
    private OrthographicCamera2D camera;

    private Vector2f delta = new Vector2f();

    public CameraFollowSystem() {
        super(Aspect.all(CameraFollow.class, Transform.class));
    }

    @Override
    protected void process(int entityId) {
        CameraFollow cameraFollow = mCameraFollow.get(entityId);
        Transform transform = mTransform.get(entityId);
        camera.writePrevTransform();

        delta.set(transform.getPosition()).sub(camera.getPosition());

        if (delta.length() >= cameraFollow.getMinDelta()) {
            camera.move(delta.mul(cameraFollow.getTween() * world.delta));
        }
    }
}
