package com.bytesmyth.lifegame;

import com.bytesmyth.graphics.batch.SpriteBatcher;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.graphics.sprite.Sprite;
import com.bytesmyth.graphics.texture.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Graphics {

    private final SpriteBatcher batcher;
    private final OrthographicCamera2D camera;

    private final List<TextureGraphicElement> quadTextureQueue = new ArrayList<>();
    private int queueLength = 0;

    private final SpriteRegistry spriteRegistry;
    private final float widthScale;

    public Graphics(OrthographicCamera2D camera, SpriteRegistry spriteRegistry, float widthScale) {
        this.batcher = new SpriteBatcher(camera);
        this.camera = camera;
        this.spriteRegistry = spriteRegistry;
        this.widthScale = widthScale;

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

    public void render(float alpha) {
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
            position.set(e.prevPosition).lerp(e.position, alpha);
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

    public Vector4f getViewBounds() {
        return camera.getViewBounds();
    }

    public Vector2f toCameraCoordinates(Vector2f screen) {
        return camera.toCameraCoordinates(screen);
    }

    public SpriteRegistry getSpriteRegistry() {
        return spriteRegistry;
    }

    public void setScreenSize(int width, int height) {
        float ratio = width / widthScale;
        float heightWorld = height / ratio;

        camera.setWindowSize(width, height);
        camera.setCameraView(-widthScale / 2f, widthScale / 2f, -heightWorld / 2f, heightWorld / 2f, 0, 100);
    }

    public OrthographicCamera2D getCamera() {
        return camera;
    }

    public SpriteBatcher getBatcher() {
        return batcher;
    }

    private static class TextureGraphicElement {
        private Sprite sprite;
        private final Vector2f position = new Vector2f();
        private final Vector2f prevPosition = new Vector2f();

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
