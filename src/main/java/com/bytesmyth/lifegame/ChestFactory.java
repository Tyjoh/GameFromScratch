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

    private LifeGame game;
    private GuiManager guiManager;

    public ChestFactory(LifeGame game, GuiManager guiManager) {
        this.game = game;
        this.guiManager = guiManager;
    }

    public void create(int x, int y) {
        TileMap map = game.getMap();
        World world = game.getWorld();

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
