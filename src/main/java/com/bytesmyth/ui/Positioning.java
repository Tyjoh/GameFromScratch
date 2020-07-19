package com.bytesmyth.ui;

public interface Positioning {

    Position position(Position parentPosition, Node node);

    default Position position(Position parentPosition, Node node, Gui gui) {
        return position(parentPosition, node);
    }

}
