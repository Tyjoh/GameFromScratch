package com.bytesmyth.graphics.ui.positioning;

import com.bytesmyth.graphics.ui.Node;
import org.joml.Vector2f;

public interface Positioning {

    Vector2f position(Vector2f parentSize, Node node);

}
