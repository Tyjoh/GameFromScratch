package com.bytesmyth.testgame.components;

import com.artemis.Component;
import com.bytesmyth.testgame.item.Inventory;

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
