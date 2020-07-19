package com.bytesmyth.ui;

public class AbsolutePositioning implements Positioning {

    private float x;
    private float y;

    public AbsolutePositioning(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Position position(Position parentPosition, Node node) {
        return new Position(x, y, node.getWidth(), node.getHeight());
    }
}
