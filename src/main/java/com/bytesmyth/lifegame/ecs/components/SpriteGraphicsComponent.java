package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Component;
import com.bytesmyth.graphics.sprite.Sprite;
import org.joml.Vector4f;

public class SpriteGraphicsComponent extends Component {

    private Sprite sprite;

    public SpriteGraphicsComponent(Sprite sprite) {
        this.sprite = sprite;
    }

    public SpriteGraphicsComponent() {
    }

    public Sprite getSprite() {
        return sprite;
    }

    public SpriteGraphicsComponent setSprite(Sprite sprite) {
        this.sprite = sprite;
        return this;
    }
}
