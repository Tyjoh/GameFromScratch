package com.bytesmyth.lifegame.domain.interaction;

import com.artemis.Entity;
import com.bytesmyth.graphics.ui.GuiManager;
import com.bytesmyth.lifegame.ecs.components.InventoryComponent;
import com.bytesmyth.lifegame.ui.Guis;
import com.bytesmyth.lifegame.ui.InventoryTransferGui;

public class InventoryInteractionHandler implements InteractionHandler {

    private GuiManager guiManager;
    private Entity inventoryEntity;

    public InventoryInteractionHandler(GuiManager guiManager, Entity entity) {
        this.guiManager = guiManager;
        this.inventoryEntity = entity;
    }

    @Override
    public void interact(Entity entity) {
        InventoryTransferGui gui = (InventoryTransferGui) guiManager.getGui(Guis.PLAYER_TRANSFER_INVENTORY);

        InventoryComponent inventory = inventoryEntity.getComponent(InventoryComponent.class);
        InventoryComponent actorInv = entity.getComponent(InventoryComponent.class);

        if (actorInv != null) {
            gui.setInventories(inventory.getInventory(), actorInv.getInventory());
            guiManager.enableGui(Guis.PLAYER_TRANSFER_INVENTORY);
        }
    }
}
