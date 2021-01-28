package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Component;
import com.bytesmyth.graphics.sprite.Sprite;
import com.bytesmyth.graphics.sprite.SpriteProvider;

public class SpriteGraphicsComponent extends Component {

    private SpriteProvider spriteProvider;

    public SpriteGraphicsComponent(Sprite sprite) {
        this.spriteProvider = () -> sprite;
    }

    public SpriteGraphicsComponent() {
    }

    public Sprite getSprite() {
        return spriteProvider.getSprite();
    }

    public SpriteGraphicsComponent setSprite(Sprite sprite) {
        this.spriteProvider = () -> sprite;
        return this;
    }

    public SpriteGraphicsComponent setSpriteProvider(SpriteProvider provider) {
        this.spriteProvider = provider;
        return this;
    }
}
