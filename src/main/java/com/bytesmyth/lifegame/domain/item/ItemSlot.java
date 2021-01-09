package com.bytesmyth.lifegame.domain.item;

public class ItemSlot {

    private Inventory inventory;
    private int slotId;

    private Item item;
    private int count;

    public ItemSlot(Inventory inventory, int slotId) {
        this.inventory = inventory;
        this.slotId = slotId;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setItem(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    public int getSlotId() {
        return slotId;
    }

    public void clear() {
        item = null;
        count = 0;
    }
}
