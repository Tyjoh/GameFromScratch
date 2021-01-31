package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.application.Input;
import com.bytesmyth.lifegame.LifeGame;
import com.bytesmyth.lifegame.control.Controls;
import com.bytesmyth.lifegame.ecs.components.*;
import org.joml.Vector2f;
import org.joml.Vector2i;

@All({UserControl.class, VelocityComponent.class})
public class EntityControlSystem extends IteratingSystem {

    private ComponentMapper<UserControl> mUserControl;
    private ComponentMapper<VelocityComponent> mVelocity;
    private ComponentMapper<LookDirectionComponent> mDirection;

    @Wire
    private Controls controls;

    @Override
    protected void begin() {
        super.begin();
    }

    @Override
    protected void process(int entityId) {
        UserControl userControl = mUserControl.get(entityId);
        VelocityComponent velocityComponent = mVelocity.get(entityId);

        Vector2f dir = controls.getMovement().getValue();

        velocityComponent.getVelocity().set(dir).mul(userControl.getControlSpeed());

        if (mDirection.has(entityId)) {
            Vector2f lookDir = controls.getLookDirection().getValue();
            mDirection.get(entityId).setLookDir(lookDir);
        }

    }
}
