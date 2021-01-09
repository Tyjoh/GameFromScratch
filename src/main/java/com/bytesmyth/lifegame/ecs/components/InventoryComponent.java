package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Component;
import com.bytesmyth.lifegame.domain.item.Inventory;

public class InventoryComponent extends Component {

    private Inventory inventory;

    public Inventory getInventory() {
        return inventory;
    }

    public InventoryComponent setInventory(Inventory inventory) {
        this.inventory = inventory;
        return this;
    }
}
