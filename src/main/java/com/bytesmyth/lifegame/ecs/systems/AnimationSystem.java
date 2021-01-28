package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.graphics.sprite.SpriteAnimation;
import com.bytesmyth.lifegame.ecs.components.AnimationComponent;
import com.bytesmyth.lifegame.ecs.components.LookDirectionComponent;
import com.bytesmyth.lifegame.ecs.components.SpriteGraphicsComponent;
import com.bytesmyth.lifegame.ecs.components.VelocityComponent;

@All({AnimationComponent.class})
public class AnimationSystem extends IteratingSystem {

    private ComponentMapper<AnimationComponent> mAnimation;
    private ComponentMapper<SpriteGraphicsComponent> mSpriteGraphics;

    private ComponentMapper<LookDirectionComponent> mLookDirection;
    private ComponentMapper<VelocityComponent> mVelocity;

    @Override
    protected void process(int entityId) {
        AnimationComponent animationComponent = mAnimation.get(entityId);

        if (animationComponent.hasSpriteAnimation()) {
            SpriteAnimation spriteAnimation = animationComponent.getSpriteAnimation();
            spriteAnimation.tick(world.delta);

            String state = "idle";

            if (mVelocity.has(entityId)) {
                if (mVelocity.get(entityId).getVelocity().lengthSquared() > 0) {
                    state = "running";
                }
            }

            String direction = "right";

            if (mLookDirection.has(entityId)) {
                LookDirectionComponent dirComponent = mLookDirection.get(entityId);
                if (dirComponent.lookingLeft()) {
                    direction = "left";
                }
            }

            spriteAnimation.setCurrentAnimation(String.format("%s_%s", state, direction));
        }
    }
}
