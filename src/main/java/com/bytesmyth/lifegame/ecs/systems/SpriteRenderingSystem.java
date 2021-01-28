package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.graphics.sprite.Sprite;
import com.bytesmyth.lifegame.Graphics;
import com.bytesmyth.lifegame.ecs.components.SpriteGraphicsComponent;
import com.bytesmyth.lifegame.ecs.components.TransformComponent;
import org.joml.Vector2f;

@All({TransformComponent.class, SpriteGraphicsComponent.class})
public class SpriteRenderingSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<SpriteGraphicsComponent> mSpriteGraphics;

    @Wire
    private Graphics renderer;

    @Override
    protected void begin() {
        super.begin();
        renderer.clear();
    }

    @Override
    protected void process(int i) {
        TransformComponent transformComponent = mTransform.get(i);
        Vector2f position = transformComponent.getPosition();
        Vector2f prevPosition = transformComponent.getPrevPosition();

        SpriteGraphicsComponent graphics = mSpriteGraphics.get(i);
        Sprite sprite = graphics.getSprite();
        renderer.queueSprite(sprite, position, prevPosition);
    }
}
