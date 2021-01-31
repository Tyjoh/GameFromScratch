package com.bytesmyth.lifegame;

import com.bytesmyth.graphics.sprite.Sprite;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.lifegame.domain.item.StaticItemRenderer;

public class DefaultSpriteRegistry {

    public static SpriteRegistry create(TextureAtlas groundTiles) {
        SpriteRegistry registry = new SpriteRegistry();
        registry.registerSprite("vegetable", () -> new Sprite(groundTiles.getTexture(), groundTiles.getRegionByCoord(14, 0)));
        return registry;
    }
}
