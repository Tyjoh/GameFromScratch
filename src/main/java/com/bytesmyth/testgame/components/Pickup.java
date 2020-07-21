package com.bytesmyth.testgame.components;

import com.artemis.Component;

public class Pickup extends Component {

    private float attractRadius = 2f;
    private float pickupRadius = 0.75f;

    public float getAttractRadius() {
        return attractRadius;
    }

    public Pickup setAttractRadius(float attractRadius) {
        this.attractRadius = attractRadius;
        return this;
    }

    public float getPickupRadius() {
        return pickupRadius;
    }

    public Pickup setPickupRadius(float pickupRadius) {
        this.pickupRadius = pickupRadius;
        return this;
    }
}
