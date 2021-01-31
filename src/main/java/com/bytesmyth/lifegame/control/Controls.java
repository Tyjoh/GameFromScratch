package com.bytesmyth.lifegame.control;

import com.bytesmyth.application.Input;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Controls {

    private List<Control> controls = new LinkedList<>();

    private VectorControl movement;
    private VectorControl lookDirection;
    private ActivationControl interact;
    private ToggleControl inventory;

    public Controls(VectorControl movement, VectorControl lookDirection, ActivationControl interact, ToggleControl inventory) {
        this.movement = movement;
        this.lookDirection = lookDirection;
        this.interact = interact;
        this.inventory = inventory;

        controls.addAll(Arrays.asList(movement, lookDirection, interact, inventory));
    }

    public void poll(Input input) {
        long ts = System.nanoTime();
        for (Control control : controls) {
            control.poll(input, ts);
        }
    }

    public void tick(float dt) {
        for (Control control : controls) {
            control.tick(dt);
        }
    }

    public VectorControl getMovement() {
        return movement;
    }

    public VectorControl getLookDirection() {
        return lookDirection;
    }

    public ActivationControl getInteract() {
        return interact;
    }
}
