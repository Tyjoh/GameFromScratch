package com.bytesmyth.ui;

import com.bytesmyth.input.Input;

public class MousePositioning implements Positioning {

    private Input input;

    public MousePositioning(Input input) {
        this.input = input;
    }

    @Override
    public Position position(Position parentPosition, Node node) {
        return null;
    }

    @Override
    public Position position(Position parentPosition, Node node, Gui gui) {
        return null;
    }
}
