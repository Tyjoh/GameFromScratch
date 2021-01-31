package com.bytesmyth.lifegame.ui;

import com.bytesmyth.graphics.ui.Container;
import com.bytesmyth.graphics.ui.Gui;
import com.bytesmyth.graphics.ui.Pane;
import com.bytesmyth.graphics.ui.positioning.RelativePositioning;
import com.bytesmyth.lifegame.domain.item.Inventory;
import com.bytesmyth.lifegame.domain.item.ItemSlot;

public class PlayerInventoryUI extends Gui {

    private static final float CELL_SIZE = 32f;
    private static final float CELL_PADDING = 2f;

    private final int invWidth;
    private final int invHeight;

    private Container inventoryContainer;
    private final Pane inventoryPane;

    public PlayerInventoryUI(int invWidth, int invHeight) {
        this.invWidth = invWidth;
        this.invHeight = invHeight;

        inventoryPane = new Pane();
        inventoryPane.setPositioning(RelativePositioning.center());
        this.addNode(inventoryPane);

        ItemNode handSlot = new ItemNode(new ItemSlot(null, 0));
        handSlot.setSize(CELL_SIZE, CELL_SIZE);
        this.getMouse().setHeldNode(handSlot);
    }

    public void setCurrentInventory(Inventory inventory) {
        if (inventoryContainer != null) {
            inventoryPane.removeNode(inventoryContainer);
        }

        if (inventory != null) {
            inventoryContainer = new InventoryContainer(inventory, invWidth, invHeight, CELL_SIZE, CELL_PADDING);
            inventoryPane.addNode(inventoryContainer);
            inventoryPane.setSize(inventoryContainer.getWidth() + CELL_SIZE, inventoryContainer.getHeight() + CELL_SIZE);
        }
    }
}
