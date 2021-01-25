package com.bytesmyth.lifegame;

import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.graphics.texture.TextureRegion;
import com.bytesmyth.lifegame.tilemap.HorizontalAutoTiler;
import com.bytesmyth.lifegame.tilemap.RectangleAutoTileBuilder;
import com.bytesmyth.lifegame.tilemap.RectangleAutoTiler;
import com.bytesmyth.lifegame.tilemap.TileRegistry;

import static com.bytesmyth.lifegame.tilemap.RectangleAutoTiler.*;

public class DefaultTileRegistry {

    public static TileRegistry create(TextureAtlas tiles) {
        TileRegistry tileRegistry = new TileRegistry();

        //ground grass
        tileRegistry.register("grass", tiles.getRegionByCoord(6, 3));
        tileRegistry.register("grass", "0", tiles.getRegionByCoord(6, 3));
        tileRegistry.register("grass", "1", tiles.getRegionByCoord(7, 3));
        tileRegistry.register("grass", "2", tiles.getRegionByCoord(6, 4));
        tileRegistry.register("grass", "3", tiles.getRegionByCoord(7, 4));

        //dirt
        tileRegistry.register("dirt_wall", tiles.getRegionByCoord(2, 5));
        tileRegistry.register("dirt_wall", "left", tiles.getRegionByCoord(1, 5));
        tileRegistry.register("dirt_wall", "center", tiles.getRegionByCoord(2, 5));
        tileRegistry.register("dirt_wall", "right", tiles.getRegionByCoord(3, 5));

        //dirt top grass
        tileRegistry.register("top_grass", tiles.getRegionByCoord(2, 2));
        tileRegistry.register("top_grass", "top_left", tiles.getRegionByCoord(1, 1));
        tileRegistry.register("top_grass", "top_center", tiles.getRegionByCoord( 2, 1));
        tileRegistry.register("top_grass", "top_right", tiles.getRegionByCoord(3, 1));

        tileRegistry.register("top_grass", "center_left", tiles.getRegionByCoord(1, 2));
        tileRegistry.register("top_grass", "center", tiles.getRegionByCoord(2, 2));
        tileRegistry.register("top_grass", "center_right", tiles.getRegionByCoord(3, 2));

        tileRegistry.register("top_grass", "bottom_left", tiles.getRegionByCoord(1, 3));
        tileRegistry.register("top_grass", "bottom_center", tiles.getRegionByCoord(2, 3));
        tileRegistry.register("top_grass", "bottom_right", tiles.getRegionByCoord(3, 3));

        tileRegistry.register("top_grass", "corner_top_left", tiles.getRegionByCoord(6, 0));
        tileRegistry.register("top_grass", "corner_top_right", tiles.getRegionByCoord(7, 0));
        tileRegistry.register("top_grass", "corner_bottom_left", tiles.getRegionByCoord(6, 1));
        tileRegistry.register("top_grass", "corner_bottom_right", tiles.getRegionByCoord(7, 1));

        //dirt top grass edges
        tileRegistry.register("top_grass_edge", null);
        tileRegistry.register("top_grass_edge", "left", tiles.getRegionByCoord(0, 2));
        tileRegistry.register("top_grass_edge", "right", tiles.getRegionByCoord(4, 2));
        tileRegistry.register("top_grass_edge", "top", tiles.getRegionByCoord(2, 0));
        tileRegistry.register("top_grass_edge", "bottom", tiles.getRegionByCoord(2, 4));

        tileRegistry.register("top_grass_edge", "top_left", tiles.getRegionByCoord(4, 4));
        tileRegistry.register("top_grass_edge", "top_right", tiles.getRegionByCoord(0, 4));
        tileRegistry.register("top_grass_edge", "bottom_right", tiles.getRegionByCoord(0, 0));
        tileRegistry.register("top_grass_edge", "bottom_left", tiles.getRegionByCoord(4, 0));


        TextureRegion[] dirtTiles = new TextureRegion[] {
                tileRegistry.getRegion("dirt_wall"),
                tileRegistry.getRegion("dirt_wall", "right"),
                tileRegistry.getRegion("dirt_wall", "left"),
                tileRegistry.getRegion("dirt_wall")
        };

        HorizontalAutoTiler dirtWallTiler = new HorizontalAutoTiler("dirt_wall", dirtTiles);
        tileRegistry.registerTiler("dirt_wall", dirtWallTiler);

        RectangleAutoTiler topGrassTiler = new RectangleAutoTileBuilder("top_grass", tileRegistry.getRegion("top_grass"))
                .requireAndForbid(tileRegistry.getRegion("top_grass", "center_right"), LEFT | TOP | BOTTOM, RIGHT)
                .requireAndForbid(tileRegistry.getRegion("top_grass", "center_left"),  RIGHT | TOP | BOTTOM, LEFT)
                .requireAndForbid(tileRegistry.getRegion("top_grass", "top_center"), LEFT | RIGHT | BOTTOM, TOP)
                .requireAndForbid(tileRegistry.getRegion("top_grass", "bottom_center"), LEFT | TOP | RIGHT, BOTTOM)

                .requireAndForbid(tileRegistry.getRegion("top_grass", "top_right"), LEFT | BOTTOM, TOP | RIGHT)
                .requireAndForbid(tileRegistry.getRegion("top_grass", "top_left"), RIGHT | BOTTOM, TOP | LEFT)
                .requireAndForbid(tileRegistry.getRegion("top_grass", "bottom_left"), RIGHT | TOP, BOTTOM | LEFT)
                .requireAndForbid(tileRegistry.getRegion("top_grass", "bottom_right"), LEFT | TOP, BOTTOM | RIGHT)

                .requireAndForbid(tileRegistry.getRegion("top_grass", "corner_top_left"), TOP | RIGHT | BOTTOM | LEFT, BOTTOM_RIGHT)
                .requireAndForbid(tileRegistry.getRegion("top_grass", "corner_top_right"), TOP | RIGHT | BOTTOM | LEFT, BOTTOM_LEFT)
                .requireAndForbid(tileRegistry.getRegion("top_grass", "corner_bottom_left"), TOP | RIGHT | BOTTOM | LEFT, TOP_RIGHT)
                .requireAndForbid(tileRegistry.getRegion("top_grass", "corner_bottom_right"), TOP | RIGHT | BOTTOM | LEFT, TOP_LEFT)
                .build();
        tileRegistry.registerTiler("top_grass", topGrassTiler);

        RectangleAutoTiler topGrassEdgeTiler = new RectangleAutoTileBuilder("top_grass_edge", "top_grass", null)
                .requireAndForbid(tileRegistry.getRegion("top_grass_edge", "left"), RIGHT, TOP | BOTTOM | LEFT)
                .requireAndForbid(tileRegistry.getRegion("top_grass_edge", "right"), LEFT, TOP | BOTTOM | RIGHT)
                .requireAndForbid(tileRegistry.getRegion("top_grass_edge", "top"), BOTTOM, TOP | RIGHT | LEFT)
                .requireAndForbid(tileRegistry.getRegion("top_grass_edge", "bottom"), TOP, BOTTOM | RIGHT | LEFT)

                .requireAndForbid(tileRegistry.getRegion("top_grass_edge", "top_left"),BOTTOM | RIGHT, TOP | LEFT)
                .requireAndForbid(tileRegistry.getRegion("top_grass_edge", "top_right"),BOTTOM | LEFT, TOP | RIGHT)
                .requireAndForbid(tileRegistry.getRegion("top_grass_edge", "bottom_left"),TOP | RIGHT, BOTTOM | LEFT)
                .requireAndForbid(tileRegistry.getRegion("top_grass_edge", "bottom_right"),TOP | LEFT, BOTTOM | RIGHT)

                .build();
        tileRegistry.registerTiler("top_grass_edge", topGrassEdgeTiler);


        return tileRegistry;
    }
}
