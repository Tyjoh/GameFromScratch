package com.bytesmyth.testgame.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Exclude;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.graphics.texture.TextureRegion;
import com.bytesmyth.testgame.Renderer;
import com.bytesmyth.testgame.components.AnimatedTextureGraphics;
import com.bytesmyth.testgame.components.TexturedGraphics;
import com.bytesmyth.testgame.components.Transform;
import org.joml.Vector2f;

@All({Transform.class, TexturedGraphics.class})
public class TextureGraphicsRenderingSystem extends IteratingSystem {

    private ComponentMapper<Transform> mTransform;
    private ComponentMapper<TexturedGraphics> mTexturedGraphics;

    @Wire
    private Renderer renderer;

    @Override
    protected void begin() {
        super.begin();
        renderer.clearTextureGraphics();
    }

    @Override
    protected void process(int i) {
        Vector2f position = mTransform.get(i).getPosition();
        TexturedGraphics graphics = mTexturedGraphics.get(i);

        TextureAtlas atlas = graphics.getTextureAtlas();
        TextureRegion region = atlas.getRegionById(graphics.getTileId());
        renderer.queueTextureGraphics(graphics.getShape(), position, atlas.getTexture(), region);
    }
}
