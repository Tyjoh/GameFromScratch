package com.bytesmyth.lifegame;

import com.artemis.World;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.lifegame.tilemap.FarmTileRenderer;
import com.bytesmyth.lifegame.tilemap.StaticTileRenderer;
import com.bytesmyth.lifegame.tilemap.TileRegistry;

public class DefaultTileRegistry {

    public static TileRegistry create(World world, TextureAtlas atlas) {
        TileRegistry registry = new TileRegistry();
        registry.registerRenderer("grass", new StaticTileRenderer(atlas.getRegionById(0)));
        registry.registerRenderer("rock", new StaticTileRenderer(atlas.getRegionByCoord(16, 3)));
        registry.registerRenderer("bush", new FarmTileRenderer(world, atlas.getRegionByCoord(14, 7), atlas.getRegionByCoord(13, 7)));
        registry.registerRenderer("tall_grass", new StaticTileRenderer(atlas.getRegionByCoord(15, 7)));
        registry.registerRenderer("barrel", new StaticTileRenderer(atlas.getRegionByCoord(11, 7)));
        registry.registerRenderer("crate", new StaticTileRenderer(atlas.getRegionByCoord(12, 7)));
        registry.registerRenderer("lamp_base", new StaticTileRenderer(atlas.getRegionByCoord(12, 7)));
        return registry;
    }
}
