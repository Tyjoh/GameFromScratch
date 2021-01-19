package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.graphics.texture.TextureRegion;
import com.bytesmyth.lifegame.Renderer;
import com.bytesmyth.lifegame.ecs.components.SpriteGraphicsComponent;
import com.bytesmyth.lifegame.ecs.components.TransformComponent;
import com.bytesmyth.lifegame.ecs.components.VelocityComponent;
import org.joml.Vector2f;

@All({TransformComponent.class, SpriteGraphicsComponent.class})
public class TextureGraphicsRenderingSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<SpriteGraphicsComponent> mTexturedGraphics;
    private ComponentMapper<VelocityComponent> mVelocity;

    @Wire
    private Renderer renderer;

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

        SpriteGraphicsComponent graphics = mTexturedGraphics.get(i);

        TextureAtlas atlas = graphics.getTextureAtlas();
        TextureRegion region = atlas.getRegionById(graphics.getTileId());

        renderer.queueTextureGraphics(graphics.getShape(), position, prevPosition, atlas.getTexture(), region);
    }
}
