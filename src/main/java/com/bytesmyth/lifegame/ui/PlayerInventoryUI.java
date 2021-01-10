package com.bytesmyth.lifegame.ui;

import com.bytesmyth.graphics.ui.*;
import com.bytesmyth.graphics.ui.positioning.RelativePositioning;
import com.bytesmyth.lifegame.domain.item.Inventory;
import com.bytesmyth.lifegame.domain.item.ItemSlot;

public class PlayerInventoryUI extends Gui {

    private static final float CELL_SIZE = 32f;
    private static final float CELL_PADDING = 2f;

    private final int invWidth;
    private final int invHeight;

    private final Container slotContainer;

    public PlayerInventoryUI(int invWidth, int invHeight) {
        this.invWidth = invWidth;
        this.invHeight = invHeight;

        float width = (invWidth * CELL_SIZE) + ((invWidth-1) * CELL_PADDING);
        float height = (invHeight * CELL_SIZE) + ((invHeight-1) * CELL_PADDING);

        Pane inventoryPane = new Pane();
        inventoryPane.setSize(width + CELL_SIZE, height + CELL_SIZE);
        inventoryPane.setPositioning(RelativePositioning.center());
        this.addNode(inventoryPane);

        slotContainer = new Container();
        slotContainer.setPositioning(RelativePositioning.center());
        slotContainer.setSize(width, height);
        inventoryPane.addNode(slotContainer);

        ItemNode handSlot = new ItemNode(new ItemSlot(null, 0));
        handSlot.setSize(CELL_SIZE, CELL_SIZE);
        this.getMouse().setHeldNode(handSlot);
    }

    public void setCurrentInventory(Inventory inventory) {
        slotContainer.clearNodes();
        if (inventory != null) {
            initializeInventory(inventory);
        }
    }

    private void initializeInventory(Inventory inventory) {
        int slotNum = 0;
        for (int y = 0; y < invHeight; y++) {
            for (int x = 0; x < invWidth; x++) {
                ItemSlot itemSlot = inventory.getSlot(slotNum);

                ItemSlotPane cell = new ItemSlotPane(itemSlot);
                cell.setPosition((x * CELL_SIZE) + (x * CELL_PADDING), -(y * CELL_SIZE) - (y * CELL_PADDING));
                cell.setSize(CELL_SIZE, CELL_SIZE);
                cell.setOpacity(0.5f);

                slotContainer.addNode(cell);

                slotNum++;
            }
        }
    }
}
