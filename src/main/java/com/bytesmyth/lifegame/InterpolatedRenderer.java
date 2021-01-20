package com.bytesmyth.lifegame;

import com.bytesmyth.graphics.batch.QuadTextureBatcher;
import com.bytesmyth.graphics.mesh.Rectangle;
import com.bytesmyth.graphics.sprite.Sprite;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureRegion;
import com.bytesmyth.lifegame.ecs.components.SpriteGraphicsComponent;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InterpolatedRenderer {

    private final QuadTextureBatcher batcher;

    private final List<TextureGraphicElement> quadTextureQueue = new ArrayList<>();
    private int queueLength = 0;

    public InterpolatedRenderer(QuadTextureBatcher batcher) {
        this.batcher = batcher;
        for (int i = 0; i < 100; i++) {
            quadTextureQueue.add(new TextureGraphicElement());
        }
    }

    public void clear() {
        queueLength = 0;
    }

    public void queueSprite(Sprite sprite, Vector2f position, Vector2f prevPosition) {
        TextureGraphicElement element = getQueueElement();
        element.setSprite(sprite)
                .setPosition(position)
                .setPrevPosition(prevPosition);
    }

    public void queueSprite(Sprite sprite, Vector2f position) {
        queueSprite(sprite, position, position);
    }

    public void render(float dt) {
        Vector2f position = new Vector2f();

        List<TextureGraphicElement> sublist = quadTextureQueue.subList(0, queueLength);
        if (sublist.isEmpty()) return;

        sublist.sort(Comparator.comparing(e -> e.sprite.getTexture().getId()));

        int prevTextureId = -1;
        if (sublist.size() > 0) {
            Texture texture = sublist.get(0).sprite.getTexture();
            batcher.begin(texture);
            prevTextureId = texture.getId();
        }

        for (TextureGraphicElement e : sublist) {
            position.set(e.prevPosition).lerp(e.position, dt);
            Sprite sprite = e.sprite;

            if (sprite.getTexture().getId() != prevTextureId) {
                batcher.end();
            }

            batcher.begin(sprite.getTexture());
            prevTextureId = sprite.getTexture().getId();

            float x1 = position.x - sprite.getOrigin().x;
            float y1 = position.y + sprite.getOrigin().y;
            float x2 = x1 + sprite.getWidth();
            float y2 = y1 - sprite.getHeight();

            batcher.draw(x1, y1, x2, y2, sprite.getTextureRegion());
        }

        if (sublist.size() > 0) {
            batcher.end();
        }
    }

    private TextureGraphicElement getQueueElement() {
        if (quadTextureQueue.size() == queueLength) {
            for (int i = 0; i < 100; i++) {
                quadTextureQueue.add(new TextureGraphicElement());
            }
            System.out.println("Expanded graphic element pool to " + quadTextureQueue.size());
        }

        TextureGraphicElement e = quadTextureQueue.get(queueLength);
        queueLength++;
        return e;
    }

    private static class TextureGraphicElement {
        private Sprite sprite;
        private Vector2f position = new Vector2f();
        private Vector2f prevPosition = new Vector2f();

        public TextureGraphicElement setSprite(Sprite sprite) {
            this.sprite = sprite;
            return this;
        }

        public TextureGraphicElement setPosition(Vector2f position) {
            this.position.set(position);
            return this;
        }

        public TextureGraphicElement setPrevPosition(Vector2f prevPosition) {
            this.prevPosition.set(prevPosition);
            return this;
        }
    }

}
