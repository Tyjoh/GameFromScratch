package com.bytesmyth.testgame.item;

import java.util.Optional;

public class Inventory {

    private ItemSlot[] slots;

    public Inventory(int size) {
        slots = new ItemSlot[size];
        for (int i = size - 1; i >= 0; i--) {
            slots[i] = new ItemSlot(this, i);
        }
    }

    public ItemSlot getSlot(int slot) {
        if (slot >= slots.length) {
            throw new IllegalArgumentException("Slot " + slot + " out of bounds. Inventory size: " + size());
        }
        return slots[slot];
    }

    public boolean hasAvailable() {
        return getAvailable().isPresent();
    }

    public Optional<ItemSlot> getOccupied() {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].getItem() != null) {
                return Optional.of(slots[i]);
            }
        }
        return Optional.empty();
    }

    public Optional<ItemSlot> getAvailable() {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].getItem() == null) {
                return Optional.of(slots[i]);
            }
        }
        return Optional.empty();
    }

    public Optional<ItemSlot> getAvailable(Item item) {
        for (int i = 0; i < slots.length; i++) {
            ItemSlot slot = slots[i];
            if (slot.getItem() != null && slot.getItem().equals(item) && slot.getCount() < 10) {
                return Optional.of(slots[i]);
            }
        }

        return getAvailable();
    }

    public int size() {
        return slots.length;
    }

}
