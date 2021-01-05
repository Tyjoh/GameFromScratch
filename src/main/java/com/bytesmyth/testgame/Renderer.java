package com.bytesmyth.testgame;

import com.bytesmyth.graphics.batch.QuadTextureBatcher;
import com.bytesmyth.graphics.mesh.Rectangle;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureRegion;
import com.bytesmyth.testgame.components.TexturedGraphics;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Renderer {

    private final QuadTextureBatcher batcher;

    private final List<TextureGraphicElement> quadTextureQueue = new ArrayList<>();
    private int queueLength = 0;

    public Renderer(QuadTextureBatcher batcher) {
        this.batcher = batcher;
        for (int i = 0; i < 1000; i++) {
            quadTextureQueue.add(new TextureGraphicElement());
        }
    }

    public void clear() {
        queueLength = 0;
    }

    public void queueTextureGraphics(Rectangle shape, Vector2f position, Vector2f prevPosition, Texture texture, TextureRegion region) {
        if (quadTextureQueue.size() == queueLength) {
            System.out.println("Expanding graphic element pool to: " + quadTextureQueue.size() + 100);
            for (int i = 0; i < 100; i++) {
                quadTextureQueue.add(new TextureGraphicElement());
            }
        }

        quadTextureQueue.get(queueLength++).set(shape, texture, region, position, prevPosition);
    }

    public void render(float dt) {
        Vector2f position = new Vector2f();

        List<TextureGraphicElement> sublist = quadTextureQueue.subList(0, queueLength);
        sublist.sort(Comparator.comparing(e -> e.texture.getId()));

        int prevTextureId = -1;
        if (sublist.size() > 0) {
            TextureGraphicElement e = sublist.get(0);
            batcher.begin(e.texture);
            prevTextureId = e.texture.getId();
        }

        for (TextureGraphicElement e : sublist) {
            position.set(e.prevPosition).lerp(e.position, dt);

            if (e.texture.getId() != prevTextureId) {
                batcher.end();
            }

            batcher.begin(e.texture);
            prevTextureId = e.texture.getId();
            batcher.draw(e.shape, e.region, position);
        }

        if (sublist.size() > 0) {
            batcher.end();
        }
    }

    private static class TextureGraphicElement {
        private Rectangle shape;
        private Texture texture;
        private TextureRegion region;
        private Vector2f position;
        private Vector2f prevPosition;

        private void set(TexturedGraphics graphics, Vector2f position, Vector2f prevPosition) {
            this.shape = graphics.getShape();
            this.texture = graphics.getTextureAtlas().getTexture();
            this.region = graphics.getTextureAtlas().getRegionById(graphics.getTileId());
            this.position = position;
            this.prevPosition = prevPosition;
        }

        public void set(Rectangle shape, Texture texture, TextureRegion region, Vector2f position, Vector2f prevPosition) {
            this.shape = shape;
            this.texture = texture;
            this.region = region;
            this.position = position;
            this.prevPosition = prevPosition;
        }
    }

}
