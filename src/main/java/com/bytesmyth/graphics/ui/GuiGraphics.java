package com.bytesmyth.graphics.ui;

import com.bytesmyth.graphics.sprite.SpriteBatcher;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.lifegame.domain.item.ItemRegistry;

//TODO: use generic graphics object that is used for world rendering.
public class GuiGraphics {

    private final OrthographicCamera2D camera;
    private final SpriteBatcher batcher;

    private final ItemRegistry itemRegistry;

    public GuiGraphics(OrthographicCamera2D camera, SpriteBatcher batcher, ItemRegistry itemRegistry) {
        this.camera = camera;
        this.batcher = batcher;
        this.itemRegistry = itemRegistry;
    }

    public SpriteBatcher getBatcher() {
        return batcher;
    }

    public OrthographicCamera2D getCamera() {
        return camera;
    }

    public ItemRegistry getItemRegistry() {
        return itemRegistry;
    }
}
