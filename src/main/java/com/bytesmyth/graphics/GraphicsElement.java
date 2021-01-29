package com.bytesmyth.graphics;

import com.bytesmyth.graphics.sprite.SpriteBatcher;
import org.joml.Vector2f;

public interface GraphicsElement extends Comparable<GraphicsElement> {

    int getLayer();

    void draw(SpriteBatcher batcher, float alpha);

}
