package com.bytesmyth.lifegame.domain.interaction;

import com.artemis.Entity;
import com.bytesmyth.lifegame.domain.item.Item;
import com.bytesmyth.lifegame.domain.item.ItemSlot;
import com.bytesmyth.lifegame.domain.tile.FarmTileLogic;
import com.bytesmyth.lifegame.ecs.components.*;

import java.util.Optional;

public class FarmInteractionHandler implements InteractionHandler {

    private Entity bush;

    public FarmInteractionHandler(Entity bush) {
        this.bush = bush;
    }

    @Override
    public void interact(Entity entity) {
        FarmTileLogic behavior = (FarmTileLogic) bush.getComponent(TileComponent.class).getBehavior();
        if (behavior.isMature()) {
            System.out.println("Harvesting bush with id " + bush.getId());
            Item item = behavior.harvest();

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
