package com.bytesmyth.lifegame.domain.item;

public class ItemSlot {

    private Inventory inventory;
    private int slotId;

    private Item item;
    private int count;
    private int maxCount = 10;

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

    public int getMaxCount() {
        return maxCount;
    }

    public int getAvailableSpace() {
        return maxCount - count;
    }

    public boolean isEmpty() {
        return item == null || count <= 0;
    }

    public boolean isFull() {
        return count >= maxCount;
    }

    public boolean containsSameItems(ItemSlot slot) {
        if (this.item == null) {
            return false;
        }

        if (this.isEmpty() || slot.isEmpty()) {
            return false;
        }

        return this.item.equals(slot.getItem());
    }

    public void transferTo(ItemSlot dest, int amount) {
        validateTransfer(dest);
        if (amount > count) throw new IllegalArgumentException("Slot does not contain enough items to transfer");

        this.count -= amount;
        dest.count += amount;
        dest.item = this.item;
    }

    public int drainTo(ItemSlot dest) {
        validateTransfer(dest);
        int maxAmount = dest.maxCount - dest.count;
        int amount = Math.min(maxAmount, this.count);

        this.count -= amount;
        dest.count += amount;
        dest.item = this.item;

        return amount;
    }

    public void swapWith(ItemSlot dest) {
        Item destItem = dest.getItem();
        int destCount = dest.getCount();

        dest.item = item;
        dest.count = count;

        this.item = destItem;
        this.count = destCount;
    }

    private void validateTransfer(ItemSlot dest) {
        if (this.isEmpty()) throw new IllegalStateException("Cannot transfer items from empty slot");
        if (!dest.isEmpty() && !containsSameItems(dest)) throw new IllegalArgumentException("Cannot transfer items to slot with different items");
    }

    public void add(Item item, int count) {
        if (count > this.getAvailableSpace()) throw new IllegalArgumentException("Not enough space in inventory");

        this.item = item;
        this.count += count;
    }
}
