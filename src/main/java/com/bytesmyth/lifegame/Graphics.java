package com.bytesmyth.lifegame;

import com.bytesmyth.graphics.batch.SpriteBatcher;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.graphics.sprite.Sprite;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureRegion;
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
        element.setTexture(sprite.getTexture())
                .setRegion(sprite.getTextureRegion())
                .setPosition(position)
                .setPrevPosition(prevPosition)
                .setOrigin(sprite.getOrigin().x, sprite.getOrigin().y)
                .setSize(sprite.getWidth(), sprite.getHeight())
                .setFlipHorizontal(sprite.flipHorizontal());
    }

    public void queueSprite(Sprite sprite, Vector2f position) {
        queueSprite(sprite, position, position);
    }

    public void queueQuad(Texture texture, TextureRegion textureRegion, Vector2f pos, Vector2f prevPos) {
        getQueueElement().setTexture(texture)
                .setRegion(textureRegion)
                .setPosition(pos)
                .setPrevPosition(prevPos)
                .setOrigin(0, 0)
                .setSize(1, 1);
    }

    public void queueQuad(Texture texture, TextureRegion textureRegion, Vector2f pos) {
        queueQuad(texture, textureRegion, pos, pos);
    }

    public void render(float alpha) {
        Vector2f position = new Vector2f();

        List<TextureGraphicElement> sublist = quadTextureQueue.subList(0, queueLength);
        if (sublist.isEmpty()) return;

        sublist.sort(Comparator.comparing(e -> e));

        int prevTextureId = -1;
        if (sublist.size() > 0) {
            Texture texture = sublist.get(0).texture;
            batcher.begin(texture);
            prevTextureId = texture.getId();
        }

        for (TextureGraphicElement e : sublist) {
            position.set(e.prevPosition).lerp(e.position, alpha);

            if (e.texture.getId() != prevTextureId) {
                batcher.end();
            }

            batcher.begin(e.texture);
            prevTextureId = e.texture.getId();

            float x1 = position.x - e.origin.x;
            float y1 = position.y + e.origin.y;
            float x2 = x1 + e.size.x;
            float y2 = y1 - e.size.y;

            float u1 = e.region.getU1();
            float v1 = e.region.getV1();
            float u2 = e.region.getU2();
            float v2 = e.region.getV2();

            if (e.flipHorizontal) {
                u1 = e.region.getU2();
                u2 = e.region.getU1();
            }

            batcher.draw(x1, y1, x2, y2, u1, v1, u2, v2);
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
        e.setFlipHorizontal(false);
        e.setSize(1,1);
        e.setOrigin(0, 0);
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

    private static class TextureGraphicElement implements Comparable<TextureGraphicElement> {
        private Texture texture;
        private TextureRegion region;

        private Vector2f origin = new Vector2f();
        private Vector2f size = new Vector2f();

        private final Vector2f position = new Vector2f();
        private final Vector2f prevPosition = new Vector2f();

        private boolean flipHorizontal = false;

        public TextureGraphicElement setTexture(Texture texture) {
            this.texture = texture;
            return this;
        }

        public TextureGraphicElement setRegion(TextureRegion region) {
            this.region = region;
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


        public TextureGraphicElement setOrigin(float x, float y) {
            this.origin.set(x, y);
            return this;
        }

        public TextureGraphicElement setSize(float w, float h) {
            this.size.set(w, h);
            return this;
        }

        @Override
        public int compareTo(TextureGraphicElement o) {
            return this.texture.getId() - o.texture.getId();
        }

        public TextureGraphicElement setFlipHorizontal(boolean flipHorizontal) {
            this.flipHorizontal = flipHorizontal;
            return this;
        }
    }

}
