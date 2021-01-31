package com.bytesmyth.lifegame.control;

import com.bytesmyth.application.Input;

public interface Control {

    void poll(Input input, long timestamp);

    void tick(float dt);

}
