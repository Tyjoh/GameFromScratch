package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Component;
import com.bytesmyth.graphics.animation.Animation;
import com.bytesmyth.graphics.animation.AnimationTimeline;

public class AnimatedTextureGraphics extends Component {

    private Animation animation;
    public float frameDuration = 0.25f;

    public float frameCooldown = frameDuration;
    public int currentFrame;
    public String currentAnimation;

    private boolean animating = true;

    public AnimatedTextureGraphics setAnimation(Animation animation) {
        this.animation = animation;
        return this;
    }

    public Animation getAnimation() {
        return animation;
    }

    public AnimationTimeline getCurrentAnimation() {
        return animation.getTimeline(currentAnimation);
    }

    public AnimatedTextureGraphics setFrameDuration(float frameDuration) {
        this.frameDuration = frameDuration;
        return this;
    }

    public AnimatedTextureGraphics setFrameCooldown(float frameCooldown) {
        this.frameCooldown = frameCooldown;
        return this;
    }

    public AnimatedTextureGraphics setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
        return this;
    }

    public AnimatedTextureGraphics setCurrentAnimation(String currentAnimation) {
        this.currentAnimation = currentAnimation;
        return this;
    }

    public boolean isAnimating() {
        return animating;
    }

    public void start() {
        animating = true;
    }

    public void stop() {
        animating = false;
    }

    public void advanceFrame() {
        this.currentFrame ++;
        int numFrames = getCurrentAnimation().size();
        if (this.currentFrame >= numFrames) {
            this.currentFrame = 0;
        }
        this.frameCooldown += frameDuration;
    }
}
