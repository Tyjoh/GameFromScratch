package com.bytesmyth.graphics;

import com.bytesmyth.graphics.sprite.SpriteBatcher;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.lifegame.SpriteRegistry;
import com.bytesmyth.util.Pool;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.*;

public class Graphics {

    private final SpriteBatcher batcher;
    private final OrthographicCamera2D camera;

    private final SpriteRegistry spriteRegistry;
    private final float widthScale;

    private Map<Class<? extends GraphicsElement>, Pool<? extends GraphicsElement>> elementPool = new HashMap<>();

    private final RenderLayerQueue[] layers = new RenderLayerQueue[16];

    public Graphics(OrthographicCamera2D camera, SpriteRegistry spriteRegistry, float widthScale) {
        this.batcher = new SpriteBatcher(camera);
        this.camera = camera;
        this.spriteRegistry = spriteRegistry;
        this.widthScale = widthScale;

        for (int i = 0; i < layers.length; i++) {
            layers[i] = new RenderLayerQueue();
        }

        elementPool.put(SpriteGraphicsElement.class, new Pool<>(SpriteGraphicsElement::new, 100));
        elementPool.put(StaticTextureQuadGraphicsElement.class, new Pool<>(StaticTextureQuadGraphicsElement::new, 1000));
    }

    public <T extends GraphicsElement> Pool<T> getElementPool(Class<T> clazz) {
        return (Pool<T>) elementPool.get(clazz);
    }

    public <T extends GraphicsElement> T getElement(Class<T> clazz) {
        return (T) elementPool.get(clazz).get();
    }

    public void clear() {
        for (int i = 0; i < layers.length; i++) {
            RenderLayerQueue layer = layers[i];
            for (int j = 0; j < layer.length; j++) {
                GraphicsElement element = layer.elements[j];
                getElementPool(element.getClass()).free(element);
            }
            layer.clear();
        }
    }

    public void enqueue(GraphicsElement element) {
        layers[element.getLayer()].add(element);
    }

    public void render(float alpha) {
        for (RenderLayerQueue layer : layers) {
            Arrays.sort(layer.elements, 0, layer.length);
            for (int i = 0; i < layer.length; i++) {
                layer.elements[i].draw(batcher, alpha);
            }
        }
        batcher.end();
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

    private static class RenderLayerQueue {
        private GraphicsElement[] elements = new GraphicsElement[100];
        private int length;

        private void clear() {
            length = 0;
        }

        public void add(GraphicsElement e) {
            if (length == elements.length) {
                elements = Arrays.copyOf(elements, elements.length * 2);
            }
            elements[length] = e;
            length++;
        }
    }

}
