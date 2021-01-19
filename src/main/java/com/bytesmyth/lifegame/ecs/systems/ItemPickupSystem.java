package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import com.bytesmyth.lifegame.ecs.components.InventoryComponent;
import com.bytesmyth.lifegame.ecs.components.ItemComponent;
import com.bytesmyth.lifegame.ecs.components.ItemPickupComponent;
import com.bytesmyth.lifegame.ecs.components.TransformComponent;
import com.bytesmyth.lifegame.domain.item.Inventory;
import com.bytesmyth.lifegame.domain.item.Item;
import com.bytesmyth.lifegame.domain.item.ItemSlot;
import org.joml.Vector2f;

import java.util.Optional;

@All({ItemPickupComponent.class, InventoryComponent.class, TransformComponent.class})
public class ItemPickupSystem extends IteratingSystem {

    private ComponentMapper<ItemPickupComponent> mPickup;
    private ComponentMapper<InventoryComponent> mInventory;
    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<ItemComponent> mItemComponent;

    private EntitySubscription collectableItems;

    @Override
    protected void initialize() {
        super.initialize();
        collectableItems = world.getAspectSubscriptionManager().get(Aspect.all(ItemComponent.class, TransformComponent.class));
    }

    private Vector2f diff = new Vector2f();

    @Override
    protected void process(int entityId) {
        Vector2f target = mTransform.get(entityId).getPosition();
        ItemPickupComponent itemPickup = mPickup.get(entityId);
        float pickupRad2 = itemPickup.getPickupRadius() * itemPickup.getPickupRadius();
        float attractRad2 = itemPickup.getAttractRadius() * itemPickup.getAttractRadius();
        Inventory inventory = mInventory.get(entityId).getInventory();

        IntBag items = collectableItems.getEntities();
        for (int i = 0; i < items.size(); i++) {
            int itemId = items.get(i);
            Vector2f itemPos = mTransform.get(itemId).getPosition();
            float len2 = diff.set(target).sub(itemPos).lengthSquared();

            Item item = mItemComponent.get(itemId).getItem();
            Optional<ItemSlot> available = inventory.getAvailable(item);

            if (len2 <= pickupRad2 && available.isPresent()) {
                available.get().setItem(item, available.get().getCount() + 1);
                mTransform.remove(itemId);
            } else if (len2 <= attractRad2) {
                diff.normalize().mul(world.delta);
                mTransform.get(itemId).getPosition().add(diff);
            }
        }
    }
}
