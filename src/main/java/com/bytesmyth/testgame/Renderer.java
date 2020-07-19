package com.bytesmyth.testgame;

import com.bytesmyth.graphics.batch.QuadTextureBatcher;
import com.bytesmyth.graphics.mesh.Rectangle;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureRegion;
import com.bytesmyth.testgame.components.TexturedGraphics;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Renderer {

    private QuadTextureBatcher batcher;

    private List<TextureGraphicElement> quadTextureQueue = new ArrayList<>();

    public Renderer(QuadTextureBatcher batcher) {
        this.batcher = batcher;
    }

    public void clearTextureGraphics() {
        quadTextureQueue.clear();
    }

    public void queueTextureGraphics(Rectangle shape, Vector2f position, Texture texture, TextureRegion region) {
        quadTextureQueue.add(new TextureGraphicElement(shape, texture, region, position));
    }

    public void render(float dt) {
        for (TextureGraphicElement e : quadTextureQueue) {
            batcher.begin(e.texture);
            batcher.draw(e.shape, e.region, e.position);
            batcher.end();
        }
    }

    private static class TextureGraphicElement {
        private Rectangle shape;
        private Texture texture;
        private TextureRegion region;
        private Vector2f position;

        private TextureGraphicElement(TexturedGraphics graphics, Vector2f position) {
            this.shape = graphics.getShape();
            this.texture = graphics.getTextureAtlas().getTexture();
            this.region = graphics.getTextureAtlas().getRegionById(graphics.getTileId());
            this.position = position;
        }

        public TextureGraphicElement(Rectangle shape, Texture texture, TextureRegion region, Vector2f position) {
            this.shape = shape;
            this.texture = texture;
            this.region = region;
            this.position = position;
        }
    }

}
