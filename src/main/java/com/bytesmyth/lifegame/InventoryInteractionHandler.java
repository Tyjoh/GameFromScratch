package com.bytesmyth.lifegame;

import com.artemis.Entity;
import com.bytesmyth.graphics.ui.GuiManager;
import com.bytesmyth.lifegame.ecs.components.InteractionHandler;
import com.bytesmyth.lifegame.ecs.components.InventoryComponent;
import com.bytesmyth.lifegame.ui.InventoryTransferGui;

import static com.bytesmyth.lifegame.LifeGame.TRANSFER_INVENTORY;

public class InventoryInteractionHandler implements InteractionHandler {

    private GuiManager guiManager;
    private Entity inventoryEntity;

    public InventoryInteractionHandler(GuiManager guiManager, Entity entity) {
        this.guiManager = guiManager;
        this.inventoryEntity = entity;
    }

    @Override
    public void interact(Entity entity) {
        InventoryTransferGui gui = (InventoryTransferGui) guiManager.getGui(TRANSFER_INVENTORY);

        InventoryComponent inventory = inventoryEntity.getComponent(InventoryComponent.class);
        InventoryComponent actorInv = entity.getComponent(InventoryComponent.class);

        if (actorInv != null) {
            gui.setInventories(inventory.getInventory(), actorInv.getInventory());
            guiManager.enableGui(TRANSFER_INVENTORY);
        }
    }
}
