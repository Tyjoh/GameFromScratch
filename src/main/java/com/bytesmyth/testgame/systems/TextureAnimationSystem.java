package com.bytesmyth.testgame.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.graphics.animation.AnimationTimeline;
import com.bytesmyth.testgame.components.AnimatedTextureGraphics;
import com.bytesmyth.testgame.components.TexturedGraphics;

@All({TexturedGraphics.class, AnimatedTextureGraphics.class})
public class TextureAnimationSystem extends IteratingSystem {

    private ComponentMapper<TexturedGraphics> mTexturedGraphics;
    private ComponentMapper<AnimatedTextureGraphics> mAnimatedTexturedGraphics;

    @Override
    protected void process(int i) {
        TexturedGraphics textureGraphics = mTexturedGraphics.get(i);
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
