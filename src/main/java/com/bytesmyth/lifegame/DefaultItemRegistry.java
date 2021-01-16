package com.bytesmyth.lifegame;

import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.lifegame.domain.item.ItemRegistry;
import com.bytesmyth.lifegame.domain.item.StaticItemRenderer;

public class DefaultItemRegistry {

    public static ItemRegistry create(TextureAtlas atlas) {
        ItemRegistry registry = new ItemRegistry();
        registry.register("coin", new StaticItemRenderer(atlas.getRegionByCoord(0, 24)));
        registry.register("berry", new StaticItemRenderer(atlas.getRegionByCoord(1, 24)));
        return registry;
    }
}
