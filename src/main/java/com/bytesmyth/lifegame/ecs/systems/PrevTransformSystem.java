package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.lifegame.ecs.components.TransformComponent;

public class PrevTransformSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> mTransform;

    public PrevTransformSystem() {
        super(Aspect.all(TransformComponent.class));
    }

    @Override
    protected void process(int i) {
        mTransform.get(i).writePrevious();
    }
}
