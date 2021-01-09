package com.bytesmyth.graphics.ui.positioning;

import com.bytesmyth.graphics.ui.Node;
import org.joml.Vector2f;

public class DefaultPositioning implements Positioning {

    @Override
    public Vector2f position(Vector2f parentSize, Node node) {
        return node.getPosition();
    }
}
