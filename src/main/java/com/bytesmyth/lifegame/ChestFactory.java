package com.bytesmyth.lifegame;

import com.artemis.Entity;
import com.artemis.World;
import com.bytesmyth.graphics.ui.GuiManager;
import com.bytesmyth.lifegame.domain.interaction.InventoryInteractionHandler;
import com.bytesmyth.lifegame.domain.item.Inventory;
import com.bytesmyth.lifegame.ecs.components.InteractiveComponent;
import com.bytesmyth.lifegame.ecs.components.InventoryComponent;
import com.bytesmyth.lifegame.ecs.components.TileComponent;
import com.bytesmyth.lifegame.tilemap.Tile;
import com.bytesmyth.lifegame.tilemap.TileMap;

public class ChestFactory {

    private TileMap map;
    private World world;
    private GuiManager guiManager;

    public ChestFactory(TileMap map, World world, GuiManager guiManager) {
        this.map = map;
        this.world = world;
        this.guiManager = guiManager;
    }

    public void create(int x, int y) {
        Tile tile = map.getLayer("1").getTile(x, y);
        if (tile != null && tile.isDynamic()) {
            world.delete(tile.getEntityId());
        }

        Tile crateTile = new Tile("crate");
        map.getLayer("1").setTile(x, y, crateTile);
        map.getLayer("collision").setTile(x, y, new Tile("solid"));

        Entity entity = world.createEntity();
        crateTile.setDynamicEntityId(entity.getId());

        TileComponent tileComponent = new TileComponent("1", x, y);
        entity.edit().add(new InventoryComponent().setInventory(new Inventory(15)));
        entity.edit().add(new InteractiveComponent(new InventoryInteractionHandler(guiManager, entity)));
        entity.edit().add(tileComponent);
    }
}
