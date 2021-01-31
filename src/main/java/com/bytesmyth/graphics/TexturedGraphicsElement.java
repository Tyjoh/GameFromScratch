package com.bytesmyth.graphics;

import com.bytesmyth.graphics.texture.Texture;
import org.joml.Vector2f;

public abstract class TexturedGraphicsElement implements GraphicsElement {

    abstract Texture getTexture();

    abstract Vector2f getPosition();

    @Override
    public int compareTo(GraphicsElement o) {
        if (o instanceof TexturedGraphicsElement) {
            int otherTexId = ((TexturedGraphicsElement) o).getTexture().getId();
            Vector2f otherPos = ((TexturedGraphicsElement) o).getPosition();

            Vector2f pos = getPosition();

            int cmpPos = Float.compare(otherPos.y, pos.y);
            if (cmpPos == 0)
                return Integer.compare(otherTexId, getTexture().getId());
            else
                return cmpPos;
        }
        return 0;
    }
}
