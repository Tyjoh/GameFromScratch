package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.graphics.texture.TextureRegion;
import com.bytesmyth.lifegame.Renderer;
import com.bytesmyth.lifegame.ecs.components.TexturedGraphics;
import com.bytesmyth.lifegame.ecs.components.Transform;
import com.bytesmyth.lifegame.ecs.components.Velocity;
import org.joml.Vector2f;

@All({Transform.class, TexturedGraphics.class})
public class TextureGraphicsRenderingSystem extends IteratingSystem {

    private ComponentMapper<Transform> mTransform;
    private ComponentMapper<TexturedGraphics> mTexturedGraphics;
    private ComponentMapper<Velocity> mVelocity;

    @Wire
    private Renderer renderer;

    @Override
    protected void begin() {
        super.begin();
        renderer.clear();
    }

    @Override
    protected void process(int i) {
        Transform transform = mTransform.get(i);

        Vector2f position = transform.getPosition();
        Vector2f prevPosition = transform.getPrevPosition();

        TexturedGraphics graphics = mTexturedGraphics.get(i);

        TextureAtlas atlas = graphics.getTextureAtlas();
        TextureRegion region = atlas.getRegionById(graphics.getTileId());

        renderer.queueTextureGraphics(graphics.getShape(), position, prevPosition, atlas.getTexture(), region);
    }
}
