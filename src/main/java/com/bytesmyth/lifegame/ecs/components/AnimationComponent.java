package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Component;
import com.bytesmyth.graphics.sprite.SpriteAnimation;

public class AnimationComponent extends Component {

    private SpriteAnimation spriteAnimation;

    public SpriteAnimation getSpriteAnimation() {
        return spriteAnimation;
    }

    public boolean hasSpriteAnimation() {
        return spriteAnimation != null;
    }

    public AnimationComponent setSpriteAnimation(SpriteAnimation spriteAnimation) {
        this.spriteAnimation = spriteAnimation;
        return this;
    }
}
