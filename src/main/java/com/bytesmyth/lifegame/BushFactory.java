package com.bytesmyth.lifegame;

import com.artemis.Entity;
import com.artemis.World;
import com.bytesmyth.lifegame.domain.interaction.FarmInteractionHandler;
import com.bytesmyth.lifegame.domain.tile.FarmTileLogic;
import com.bytesmyth.lifegame.ecs.components.InteractiveComponent;
import com.bytesmyth.lifegame.ecs.components.TileComponent;
import com.bytesmyth.lifegame.tilemap.Tile;
import com.bytesmyth.lifegame.tilemap.TileMap;

public class BushFactory {
    private final World world;
    private final TileMap map;

    public BushFactory(World world, TileMap map) {
        this.world = world;
        this.map = map;
    }

    public void create(int x, int y) {
        Tile bushTile = new Tile("bush");
        map.getLayer("1").setTile(x, y, bushTile);
        map.getLayer("collision").setTile(x, y, new Tile("solid"));

        Entity entity = world.createEntity();
        bushTile.setDynamicEntityId(entity.getId());

        TileComponent tileComponent = new TileComponent("1", x, y);
        tileComponent.setBehavior(new FarmTileLogic());
        entity.edit().add(tileComponent);

        entity.edit().add(new InteractiveComponent(new FarmInteractionHandler(entity)));
    }
}
