package com.bytesmyth.lifegame.ui;

import com.bytesmyth.graphics.ui.Container;
import com.bytesmyth.graphics.ui.positioning.RelativePositioning;
import com.bytesmyth.lifegame.domain.item.Inventory;
import com.bytesmyth.lifegame.domain.item.ItemSlot;

public class InventoryContainer extends Container {

    public InventoryContainer(Inventory inventory, int slotsW, int slotsH, float size, float padding) {
        int slotNum = 0;
        for (int y = 0; y < slotsH; y++) {
            for (int x = 0; x < slotsW; x++) {
                ItemSlot itemSlot = inventory.getSlot(slotNum);

                ItemSlotPane cell = new ItemSlotPane(itemSlot);
                cell.setPosition((x * size) + (x * padding), -(y * size) - (y * padding));
                cell.setSize(size, size);
                cell.setOpacity(0.5f);

                this.addNode(cell);

                slotNum++;
            }
        }

        this.setPositioning(RelativePositioning.center());
        this.setSize((slotsW * size) + ((slotsW-1) * padding), (slotsH * size) + ((slotsH-1) * padding));
    }

}
