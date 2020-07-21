package com.bytesmyth.testgame.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import com.bytesmyth.testgame.components.InventoryComponent;
import com.bytesmyth.testgame.components.ItemComponent;
import com.bytesmyth.testgame.components.Pickup;
import com.bytesmyth.testgame.components.Transform;
import com.bytesmyth.testgame.item.Inventory;
import com.bytesmyth.testgame.item.Item;
import org.joml.Vector2f;

@All({Pickup.class, InventoryComponent.class, Transform.class})
public class ItemPickupSystem extends IteratingSystem {

    private ComponentMapper<Pickup> mPickup;
    private ComponentMapper<InventoryComponent> mInventory;
    private ComponentMapper<Transform> mTransform;
    private ComponentMapper<ItemComponent> mItemComponent;

    private EntitySubscription collectableItems;

    @Override
    protected void initialize() {
        super.initialize();
        collectableItems = world.getAspectSubscriptionManager().get(Aspect.all(ItemComponent.class, Transform.class));
    }

    private Vector2f diff = new Vector2f();

    @Override
    protected void process(int entityId) {
        Vector2f target = mTransform.get(entityId).getPosition();
        Pickup pickup = mPickup.get(entityId);
        float pickupRad2 = pickup.getPickupRadius() * pickup.getPickupRadius();
        float attractRad2 = pickup.getAttractRadius() * pickup.getAttractRadius();
        Inventory inventory = mInventory.get(entityId).getInventory();

        if (!inventory.hasAvailable()) {
            return;
        }

        IntBag items = collectableItems.getEntities();
        for (int i = 0; i < items.size(); i++) {
            int itemId = items.get(i);
            Vector2f itemPos = mTransform.get(itemId).getPosition();
            float len2 = diff.set(target).sub(itemPos).lengthSquared();

            if (len2 <= pickupRad2) {
                Item item = mItemComponent.get(itemId).getItem();
                inventory.add(item);
                mTransform.remove(itemId);
            } else if (len2 <= attractRad2) {
                diff.normalize().mul(world.delta);
                mTransform.get(itemId).getPosition().add(diff);
            }
        }
    }
}
