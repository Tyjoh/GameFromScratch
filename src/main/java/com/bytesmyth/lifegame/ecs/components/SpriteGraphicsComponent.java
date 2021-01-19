package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Component;
import com.bytesmyth.graphics.mesh.Rectangle;
import com.bytesmyth.graphics.texture.TextureAtlas;

public class SpriteGraphicsComponent extends Component {

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

    public SpriteGraphicsComponent setTextureAtlas(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
        return this;
    }

    public SpriteGraphicsComponent setTileId(int tileId) {
        this.tileId = tileId;
        return this;
    }

    public SpriteGraphicsComponent setShape(Rectangle shape) {
        this.shape = shape;
        return this;
    }
}
