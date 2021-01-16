package com.bytesmyth.lifegame;

import com.artemis.Entity;
import com.bytesmyth.lifegame.domain.item.Item;
import com.bytesmyth.lifegame.domain.item.ItemSlot;
import com.bytesmyth.lifegame.ecs.components.*;

import java.util.Optional;

public class FarmInteractionHandler implements InteractionHandler {

    private Entity bush;

    public FarmInteractionHandler(Entity bush) {
        this.bush = bush;
    }

    @Override
    public void interact(Entity entity) {
        FarmTileBehavior behavior = (FarmTileBehavior) bush.getComponent(TileEntity.class).getBehavior();
        if (behavior.isMature()) {
            Item item = new Item("berry");
            System.out.println("Harvesting bush with id " + bush.getId());
            behavior.harvest();

            InventoryComponent invComponent = entity.getComponent(InventoryComponent.class);
            if (invComponent != null) {
                Optional<ItemSlot> availableSlot = invComponent.getInventory().getAvailable(item);
                availableSlot.ifPresent(slot -> slot.add(item, 1));
            } else {

            }
        } else {
            System.out.println("Bush " + bush.getId() + " not mature yet");
        }
    }
}
