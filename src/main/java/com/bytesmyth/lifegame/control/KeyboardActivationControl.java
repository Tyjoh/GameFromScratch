package com.bytesmyth.lifegame.control;

import com.bytesmyth.application.Button;
import com.bytesmyth.application.Input;

public class KeyboardActivationControl implements ActivationControl {

    private boolean justPressedDuringPoll;
    private boolean pressedDuringPoll;

    private boolean justActivated;
    private boolean activated;

    private String key;

    public KeyboardActivationControl(String key) {
        this.key = key;
    }

    @Override
    public boolean isActivated() {
        return activated;
    }

    @Override
    public boolean isJustActivated() {
        return justActivated;
    }

    @Override
    public void poll(Input input, long timestamp) {
        Button key = input.getKey(this.key);
        if (key.isJustPressed()) {
            justPressedDuringPoll = true;
        }

        if (key.isPressed()) {
            pressedDuringPoll = true;
        }
    }

    @Override
    public void tick(float dt) {
        justActivated = justPressedDuringPoll;
        activated = pressedDuringPoll;

        pressedDuringPoll = false;
        justPressedDuringPoll = false;
    }
}
