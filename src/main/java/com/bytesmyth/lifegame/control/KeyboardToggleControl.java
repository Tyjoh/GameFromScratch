package com.bytesmyth.lifegame.control;

import com.bytesmyth.application.Input;

public class KeyboardToggleControl implements ToggleControl {

    private String key;

    private boolean value = false;

    public KeyboardToggleControl(String key) {
        this.key = key;
    }

    //called on render loop
    @Override
    public void poll(Input input, long timestamp) {
        if (input.getKey(key).isJustPressed()) {
            value = !value;
        }
    }

    //called before game logic
    @Override
    public void tick(float dt) {

    }

    @Override
    public boolean getValue() {
        return value;
    }
}
