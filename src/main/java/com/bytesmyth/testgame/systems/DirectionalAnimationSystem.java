package com.bytesmyth.testgame.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.testgame.components.AnimatedTextureGraphics;
import com.bytesmyth.testgame.components.Direction;
import com.bytesmyth.testgame.components.Velocity;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector2ic;

@All({AnimatedTextureGraphics.class, Direction.class})
public class DirectionalAnimationSystem extends IteratingSystem {

    private ComponentMapper<AnimatedTextureGraphics> mAnimatedGraphics;
    private ComponentMapper<Direction> mDirection;

    @Override
    protected void process(int entityId) {
        Vector2ic direction = mDirection.get(entityId).getDir();
        AnimatedTextureGraphics animatedGraphics = mAnimatedGraphics.get(entityId);

        if (direction.length() == 0) {
            animatedGraphics.stop();
            animatedGraphics.setCurrentFrame(0);
        } else {
            animatedGraphics.start();

            if (Math.abs(direction.y()) > Math.abs(direction.x())) {
                if (direction.y() > 0) {
                    animatedGraphics.setCurrentAnimation("up");
                } else {
                    animatedGraphics.setCurrentAnimation("down");
                }
            } else {
                if (direction.x() > 0) {
                    animatedGraphics.setCurrentAnimation("right");
                } else {
                    animatedGraphics.setCurrentAnimation("left");
                }
            }
        }


    }
}
