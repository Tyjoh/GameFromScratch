package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.graphics.animation.AnimationTimeline;
import com.bytesmyth.lifegame.ecs.components.AnimatedTextureGraphics;
import com.bytesmyth.lifegame.ecs.components.SpriteGraphicsComponent;

@All({SpriteGraphicsComponent.class, AnimatedTextureGraphics.class})
public class TextureAnimationSystem extends IteratingSystem {

    private ComponentMapper<SpriteGraphicsComponent> mTexturedGraphics;
    private ComponentMapper<AnimatedTextureGraphics> mAnimatedTexturedGraphics;

    @Override
    protected void process(int i) {
        SpriteGraphicsComponent textureGraphics = mTexturedGraphics.get(i);
        AnimatedTextureGraphics animation = mAnimatedTexturedGraphics.get(i);

        if (animation.isAnimating()) {
            animation.frameCooldown -= world.delta;

            if (animation.frameCooldown <= 0) {
                animation.advanceFrame();
            }
        }

        AnimationTimeline timeline = animation.getCurrentAnimation();
        int frameTileId = timeline.getFrame(animation.currentFrame);
        textureGraphics.setTileId(frameTileId);
    }

}
