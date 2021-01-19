package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Component;

public class ItemPickupComponent extends Component {

    private float attractRadius = 3f;
    private float pickupRadius = 0.75f;

    public float getAttractRadius() {
        return attractRadius;
    }

    public ItemPickupComponent setAttractRadius(float attractRadius) {
        this.attractRadius = attractRadius;
        return this;
    }

    public float getPickupRadius() {
        return pickupRadius;
    }

    public ItemPickupComponent setPickupRadius(float pickupRadius) {
        this.pickupRadius = pickupRadius;
        return this;
    }
}
