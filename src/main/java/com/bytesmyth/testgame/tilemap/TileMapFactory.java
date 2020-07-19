package com.bytesmyth.testgame.tilemap;

import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.testgame.tilemap.tools.Fence;

public class TileMapFactory {

    public TileMap create(TextureAtlas atlas) {
        TileMap map = new TileMap(new TileMapChunkGenerator());

        VillageTileMapTools tools = new VillageTileMapTools(atlas);

        for (int i = 0; i < 32; i++) {
            int x = (int) (Math.random() * 30 + 17);
            int y = (int) (Math.random() * 30 + 17);
            tools.getPrefab("rock1").apply(x, y, map);
        }

        Fence fence = new Fence(atlas);
        fence.border(4, 60, 60, 4, map);
        fence.verticalOpening(4, 32, map);
        fence.verticalOpening(60, 32, map);
        fence.horizontalOpening(32, 4, map);
        fence.horizontalOpening(32, 60, map);

        fence.border(12, 48, 24, 24, map);
        fence.verticalOpening(12, 32, map);
        fence.horizontalOpening(14, 24, map);


        tools.getPrefab("red_wood_house").apply(26, 32, map);
        tools.getPrefab("blue_wood_house").apply(50, 40, map);
        tools.getPrefab("red_stone_house").apply(31, 17, map);
        tools.getPrefab("blue_stone_house").apply(47, 33, map);

        return map;
    }

}
