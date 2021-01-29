package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.graphics.SpriteGraphicsElement;
import com.bytesmyth.graphics.sprite.Sprite;
import com.bytesmyth.graphics.Graphics;
import com.bytesmyth.lifegame.ecs.components.SpriteGraphicsComponent;
import com.bytesmyth.lifegame.ecs.components.TransformComponent;
import com.bytesmyth.util.Pool;
import org.joml.Vector2f;

@All({TransformComponent.class, SpriteGraphicsComponent.class})
public class SpriteRenderingSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<SpriteGraphicsComponent> mSpriteGraphics;

    @Wire
    private Graphics renderer;
    private Pool<SpriteGraphicsElement> spriteElementPool;

    @Override
    protected void begin() {
        super.begin();
        renderer.clear();
        spriteElementPool = renderer.getElementPool(SpriteGraphicsElement.class);
    }

    @Override
    protected void process(int i) {
        TransformComponent transformComponent = mTransform.get(i);
        Vector2f position = transformComponent.getPosition();
        Vector2f prevPosition = transformComponent.getPrevPosition();

        SpriteGraphicsComponent graphics = mSpriteGraphics.get(i);
        Sprite sprite = graphics.getSprite();
        SpriteGraphicsElement element = spriteElementPool.get();

        element.set(sprite, position, prevPosition, 1);//TODO: dynamic layering.

        renderer.enqueue(element);
    }
}
