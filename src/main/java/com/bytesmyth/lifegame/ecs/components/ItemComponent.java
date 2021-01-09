package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Component;
import com.bytesmyth.lifegame.domain.item.Item;

public class ItemComponent extends Component {

    private Item item = new Item("coin");
    private float pickupCooldown;

    public Item getItem() {
        return item;
    }

    public ItemComponent setItem(Item item) {
        this.item = item;
        return this;
    }

    public float getPickupCooldown() {
        return pickupCooldown;
    }

    public ItemComponent setPickupCooldown(float pickupCooldown) {
        this.pickupCooldown = pickupCooldown;
        return this;
    }
}
