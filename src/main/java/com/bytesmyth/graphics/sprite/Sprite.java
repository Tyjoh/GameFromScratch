package com.bytesmyth.graphics.sprite;

import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureRegion;
import org.joml.Vector2f;

public interface Sprite {

    Texture getTexture();

    TextureRegion getTextureRegion();

    /**
     * @return local coordinates of origin. 0,0 represents lower left point. w,h is upper right.
     */
    Vector2f getOrigin();

    float getWidth();
    float getHeight();

    default void tick(float delta) {
    }

}
