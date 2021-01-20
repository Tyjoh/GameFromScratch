package com.bytesmyth.graphics.sprite;

import com.bytesmyth.graphics.animation.AnimationMap;
import com.bytesmyth.graphics.animation.Animation;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureRegion;
import org.joml.Vector2f;

public class AnimatedSprite implements Sprite {

    private Texture texture;
    private AnimationMap animationMap;

    private float width = 1;
    private float height = 1;

    private Vector2f origin = new Vector2f();

    private String currentAnimation;
    private int animationTick = 0;

    public AnimatedSprite(Texture texture, AnimationMap animationMap, String initialAnimation) {
        this.texture = texture;
        this.animationMap = animationMap;
        this.currentAnimation = initialAnimation;
    }

    public AnimatedSprite setSize(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public AnimatedSprite setOrigin(Vector2f origin) {
        this.origin.set(origin);
        return this;
    }

    public AnimatedSprite setOrigin(float x, float y) {
        this.origin.set(x, y);
        return this;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public TextureRegion getTextureRegion() {
        Animation timeline = animationMap.getAnimation(currentAnimation);
        return timeline.getFrameByTick(animationTick).getRegion();
    }

    @Override
    public Vector2f getOrigin() {
        return origin;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
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
}
