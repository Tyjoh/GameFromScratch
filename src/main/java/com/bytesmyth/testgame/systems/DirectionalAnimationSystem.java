package com.bytesmyth.testgame.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.testgame.components.AnimatedTextureGraphics;
import com.bytesmyth.testgame.components.Velocity;
import org.joml.Vector2f;

@All({AnimatedTextureGraphics.class, Velocity.class})
public class DirectionalAnimationSystem extends IteratingSystem {

    private ComponentMapper<AnimatedTextureGraphics> mAnimatedGraphics;
    private ComponentMapper<Velocity> mVelocity;

    @Override
    protected void process(int entityId) {
        Vector2f velocity = mVelocity.get(entityId).getVelocity();
        AnimatedTextureGraphics animatedGraphics = mAnimatedGraphics.get(entityId);

        if (velocity.length() == 0) {
            animatedGraphics.stop();
            animatedGraphics.setCurrentFrame(0);
        } else {
            animatedGraphics.start();

            if (Math.abs(velocity.y) > Math.abs(velocity.x)) {
                if (velocity.y > 0) {
                    animatedGraphics.setCurrentAnimation("up");
                } else {
                    animatedGraphics.setCurrentAnimation("down");
                }
            } else {
                if (velocity.x > 0) {
                    animatedGraphics.setCurrentAnimation("right");
                } else {
                    animatedGraphics.setCurrentAnimation("left");
                }
            }
        }


    }
}
