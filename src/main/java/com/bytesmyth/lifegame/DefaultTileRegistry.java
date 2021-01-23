package com.bytesmyth.lifegame;

import com.artemis.World;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.lifegame.tilemap.FarmTileRenderer;
import com.bytesmyth.lifegame.tilemap.StaticTileRenderer;
import com.bytesmyth.lifegame.tilemap.TileRegistry;

public class DefaultTileRegistry {

    public static TileRegistry create(World world, TextureAtlas atlas) {
        TileRegistry registry = new TileRegistry();

        return registry;
    }
}
