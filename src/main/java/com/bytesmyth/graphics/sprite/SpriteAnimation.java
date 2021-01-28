package com.bytesmyth.graphics.sprite;

import com.bytesmyth.graphics.animation.AnimationMap;
import com.bytesmyth.graphics.animation.Animation;
import com.bytesmyth.graphics.animation.Frame;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureRegion;
import org.joml.Vector2f;

public class SpriteAnimation implements SpriteProvider {

    private AnimationMap animationMap;

    private String currentAnimation;
    private int animationTick = 0;

    public SpriteAnimation(AnimationMap animationMap, String initialAnimation) {
        this.animationMap = animationMap;
        this.currentAnimation = initialAnimation;
    }

    public void tick(float delta) {
        animationTick++;
        if (animationTick >= animationMap.getAnimation(currentAnimation).getDurationTicks()) {
            animationTick = 0;
        }
    }

    public void setCurrentAnimation(String animationName) {
        if (!animationName.equals(currentAnimation)) {
            this.animationTick = 0;
            this.currentAnimation = animationName;
        }
    }

    public Frame getCurrentFrame() {
        return animationMap.getAnimation(currentAnimation).getFrameByTick(animationTick);
    }

    @Override
    public Sprite getSprite() {
        return getCurrentFrame().getSprite();
    }
}
