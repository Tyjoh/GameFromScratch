package com.bytesmyth.testgame.item;

import java.util.Stack;

public class Inventory {

    private Slot[] slots;
    private Stack<Slot> available = new Stack<>();

    public Inventory(int size) {
        slots = new Slot[size];
        for (int i = size - 1; i >= 0; i--) {
            slots[i] = new Slot();
            slots[i].slotNum = i;
            available.push(slots[i]);
        }
    }

    public Item getItem(int slot) {
        return slots[slot].item;
    }

    public boolean isAvailable(int slot) {
        return slots[slot].item == null;
    }

    public boolean hasAvailable() {
        return available.size() > 0;
    }

    public void insert(int slot, Item item) {
        if (slots[slot].item != null) {
            throw new IllegalArgumentException("Slot " + slot + " already occupied");
        }
        slots[slot].item = item;
        available.remove(slots[slot]);
    }

    public Item replace(int slot, Item item) {
        Item current = slots[slot].item;
        slots[slot].item = item;
        return current;
    }

    public Item remove(int slotNum) {
        Slot slot = slots[slotNum];
        Item item = slot.item;
        slot.item = null;
        available.push(slot);
        return item;
    }

    public void add(Item item) {
        if (!hasAvailable()) {
            throw new IllegalArgumentException("Inventory full");
        }
        Slot slot = available.pop();
        slot.item = item;
        System.out.println("Item " + item.getName() + " added to inventory! ");
    }

    private static class Slot {
        private int slotNum;
        private Item item;
    }

}
