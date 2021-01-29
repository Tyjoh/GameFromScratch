package com.bytesmyth.graphics;

import com.bytesmyth.graphics.sprite.Sprite;
import com.bytesmyth.graphics.sprite.SpriteBatcher;
import com.bytesmyth.graphics.texture.Texture;
import org.joml.Vector2f;

public class SpriteGraphicsElement extends TexturedGraphicsElement {

    private int layer;

    private Sprite sprite;
    private final Vector2f position = new Vector2f();
    private final Vector2f prevPosition = new Vector2f();

    private final Vector2f lerp = new Vector2f();

    public void set(Sprite sprite, Vector2f position, Vector2f prevPosition, int layer) {
        this.sprite = sprite;
        this.position.set(position);
        this.prevPosition.set(prevPosition);
        this.layer = layer;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public void draw(SpriteBatcher batcher, float alpha) {
        lerp.set(prevPosition).lerp(position, alpha);
        batcher.begin(sprite.getTexture());
        batcher.draw(sprite, lerp.x, lerp.y);
    }


    @Override
    public Texture getTexture() {
        return sprite.getTexture();
    }
}
