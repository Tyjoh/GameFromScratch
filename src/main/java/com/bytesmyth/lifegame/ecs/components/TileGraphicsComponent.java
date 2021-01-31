package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Component;
import com.bytesmyth.graphics.texture.TextureRegion;
import com.bytesmyth.util.Provider;

public class TileGraphicsComponent extends Component {
    private Provider<TextureRegion> textureProvider;

    public TileGraphicsComponent setTextureProvider(Provider<TextureRegion> textureProvider) {
        this.textureProvider = textureProvider;
        return this;
    }

    public TileGraphicsComponent setTextureRegion(TextureRegion texture) {
        this.textureProvider = Provider.constant(texture);
        return this;
    }

    public TextureRegion getTextureRegion() {
        return textureProvider.get();
    }
}
