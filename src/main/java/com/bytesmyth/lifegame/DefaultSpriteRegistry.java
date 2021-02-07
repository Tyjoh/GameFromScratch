package com.bytesmyth.lifegame;

import com.bytesmyth.graphics.sprite.Sprite;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.graphics.tileset.Tileset;
import com.bytesmyth.lifegame.domain.item.StaticItemRenderer;

public class DefaultSpriteRegistry {

    public static SpriteRegistry create(Tileset groundTiles) {
        SpriteRegistry registry = new SpriteRegistry();
        registry.registerSprite("vegetable", () -> new Sprite(groundTiles.getTexture(), groundTiles.getTile(14, 0).getRegion()));
        return registry;
    }
}
