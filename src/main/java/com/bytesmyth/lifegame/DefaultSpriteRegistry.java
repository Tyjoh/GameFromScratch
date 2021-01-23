package com.bytesmyth.lifegame;

import com.bytesmyth.graphics.sprite.StateSprite;
import com.bytesmyth.graphics.sprite.StaticSprite;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.graphics.texture.TextureRegion;
import com.bytesmyth.lifegame.tilemap.FarmTileRenderer;
import com.bytesmyth.lifegame.tilemap.StaticTileRenderer;

public class DefaultSpriteRegistry {

    private static final float ITEM_SIZE = 0.75f;

    public static SpriteRegistry create(TextureAtlas tiles, TextureAtlas items) {
        SpriteRegistry registry = new SpriteRegistry();

        registry.registerStaticSprite("coin", staticItem(items,0,24));
        registry.registerStaticSprite("berry", staticItem(items,1,24));

        registry.registerStaticSprite("grass", staticTile(tiles, 0,0));
        registry.registerStaticSprite("rock", staticTile(tiles,16, 3));
        registry.registerStaticSprite("tall_grass", staticTile(tiles,15, 7));
        registry.registerStaticSprite("barrel", staticTile(tiles,11, 7));
        registry.registerStaticSprite("crate", staticTile(tiles,12, 7));
        registry.registerStaticSprite("lamp_base", staticTile(tiles,12, 7));

        TextureRegion bushStage0 = tiles.getRegionByCoord(14, 7);
        TextureRegion bushStage1 = tiles.getRegionByCoord(13, 7);
        SpriteRegistry.SpriteFactory bushSpriteFactory = () -> new StateSprite(tiles.getTexture(), new TextureRegion[]{bushStage0, bushStage1});
        registry.registerDynamicSprite("bush", bushSpriteFactory);

        return registry;
    }

    private static StaticSprite staticItem(TextureAtlas atlas, int x, int y) {
        return new StaticSprite(atlas.getTexture(), atlas.getRegionByCoord(x, y))
                .setSize(ITEM_SIZE, ITEM_SIZE)
                .setOrigin(ITEM_SIZE/2f, ITEM_SIZE/2f);
    }

    private static StaticSprite staticTile(TextureAtlas atlas, int x, int y) {
        return new StaticSprite(atlas.getTexture(), atlas.getRegionByCoord(x, y))
                .setSize(1, 1)
                .setOrigin(0,0);
    }

}
