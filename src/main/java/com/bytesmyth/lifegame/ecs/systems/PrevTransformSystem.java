package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.lifegame.ecs.components.Transform;

public class PrevTransformSystem extends IteratingSystem {

    private ComponentMapper<Transform> mTransform;

    public PrevTransformSystem() {
        super(Aspect.all(Transform.class));
    }

    @Override
    protected void process(int i) {
        mTransform.get(i).writePrevious();
    }
}
