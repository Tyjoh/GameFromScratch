package com.bytesmyth.testgame.components;

import com.artemis.Component;
import com.bytesmyth.graphics.mesh.Rectangle;
import com.bytesmyth.graphics.texture.TextureAtlas;

public class TexturedGraphics extends Component {

    private TextureAtlas textureAtlas;
    private int tileId;
    private Rectangle shape;

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public int getTileId() {
        return tileId;
    }

    public Rectangle getShape() {
        return shape;
    }

    public TexturedGraphics setTextureAtlas(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
        return this;
    }

    public TexturedGraphics setTileId(int tileId) {
        this.tileId = tileId;
        return this;
    }

    public TexturedGraphics setShape(Rectangle shape) {
        this.shape = shape;
        return this;
    }
}
