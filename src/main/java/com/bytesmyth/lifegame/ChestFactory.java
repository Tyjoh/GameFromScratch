package com.bytesmyth.lifegame;

import com.artemis.Entity;
import com.artemis.World;
import com.bytesmyth.graphics.tileset.Tileset;
import com.bytesmyth.graphics.ui.GuiManager;
import com.bytesmyth.lifegame.domain.interaction.InventoryInteractionHandler;
import com.bytesmyth.lifegame.domain.item.Inventory;
import com.bytesmyth.lifegame.ecs.components.InteractiveComponent;
import com.bytesmyth.lifegame.ecs.components.InventoryComponent;
import com.bytesmyth.lifegame.ecs.components.TileComponent;
import com.bytesmyth.lifegame.ecs.components.TileGraphicsComponent;
import com.bytesmyth.lifegame.tilemap.Tile;
import com.bytesmyth.lifegame.tilemap.TileMap;

public class ChestFactory {

    private LifeGame game;

    public ChestFactory(LifeGame game) {
        this.game = game;
    }

    public void create(int x, int y) {
        TileMap map = game.getMap();
        World world = game.getWorld();
        Tileset tileset = game.getTileset();
        GuiManager guiManager = game.getGuiManager();

        Tile tile = map.getLayer("1").getTile(x, y);
        if (tile != null && tile.isDynamic()) {
            world.delete(tile.getEntityId());
        }

        Entity entity = world.createEntity();

        Tile newTile = new Tile("chest").setDynamicEntityId(entity.getId());
        map.getLayer("1").setTile(x, y, newTile);
        map.getLayer("collision").setTile(x, y, new Tile("solid").setDynamicEntityId(entity.getId()));

        TileComponent tileComponent = new TileComponent("1", "chest", x, y);
        entity.edit().add(new InventoryComponent().setInventory(new Inventory(15)))
                .add(new InteractiveComponent(new InventoryInteractionHandler(guiManager, entity)))
                .add(new TileGraphicsComponent().setTextureRegion(tileset.getTile("chest").getRegion()))
                .add(tileComponent);
    }
}
