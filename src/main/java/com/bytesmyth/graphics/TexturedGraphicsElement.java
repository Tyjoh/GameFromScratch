package com.bytesmyth.graphics;

import com.bytesmyth.graphics.texture.Texture;

public abstract class TexturedGraphicsElement implements GraphicsElement {

    abstract Texture getTexture();

    @Override
    public int compareTo(GraphicsElement o) {
        if (o instanceof TexturedGraphicsElement) {
            int otherTexId = ((TexturedGraphicsElement) o).getTexture().getId();
            return Integer.compare(this.getTexture().getId(), otherTexId);
        }
        return 0;
    }
}
