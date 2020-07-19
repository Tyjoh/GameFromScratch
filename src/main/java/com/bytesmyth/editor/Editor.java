package com.bytesmyth.editor;

import com.bytesmyth.input.Input;
import com.bytesmyth.game.DrawHandler;
import com.bytesmyth.game.TickHandler;

public class Editor implements TickHandler, DrawHandler {

    private Input input;

    public Editor(Input input) {
        this.input = input;
    }

    @Override
    public void draw(float dt) {

    }

    @Override
    public void tick(float dt) {

    }
}
