package com.bytesmyth.testgame.ui;

import com.bytesmyth.testgame.item.Inventory;
import com.bytesmyth.ui.*;

public class PlayerInventoryUI extends Gui {

    private final int invWidth;
    private final int invHeight;
    private Inventory currentInventory;

    private ItemSlotPane[] itemSlotPanes;
    private final Pane inventoryPane;

    public PlayerInventoryUI(int invWidth, int invHeight) {
        this.invWidth = invWidth;
        this.invHeight = invHeight;

        float cellSize = 48f;
        float padding = 2f;

        float width = (invWidth * cellSize) + ((invWidth-1) * padding);
        float height = (invHeight * cellSize) + ((invHeight-1) * padding);

        inventoryPane = new Pane();
        inventoryPane.setSize(width + cellSize, height + cellSize)
                .setKey("player_inventory_pane");

        addChild(inventoryPane);
    }

    public void setCurrentInventory(Inventory inventory) {
        inventoryPane.clearChildren();
        if (inventory != null) {
            initializeInventory(inventory);
        }
        this.currentInventory = inventory;
    }

    private void initializeInventory(Inventory inventory) {
        float cellSize = 48f;
        float padding = 2f;

        float width = (invWidth * cellSize) + ((invWidth-1) * padding);
        float height = (invHeight * cellSize) + ((invHeight-1) * padding);

        float sx = -width / 2f + cellSize / 2f;
        float sy = height / 2f - cellSize / 2f;

        itemSlotPanes = new ItemSlotPane[invWidth * invHeight];

        int slotNum = 0;
        for (int y = 0; y < invHeight; y++) {
            for (int x = 0; x < invWidth; x++) {
                ItemSlotPane cell = new ItemSlotPane();
                cell.setItemSlot(inventory.getSlot(slotNum));
                cell.setSize(cellSize, cellSize);
                itemSlotPanes[slotNum] = cell;

                cell.setKey("slot_" + slotNum);
                cell.setOpacity(0.5f);

                RelativePositioning basicPositioning = new RelativePositioning(
                        HorizontalAlignment.CENTER,
                        VerticalAlignment.CENTER,
                        sx + (x * cellSize) + (x * padding),
                        sy - (y * cellSize) - (y * padding));

                cell.setPositioning(basicPositioning);
                inventoryPane.addChild(cell);

                slotNum++;
            }
        }
    }
}
