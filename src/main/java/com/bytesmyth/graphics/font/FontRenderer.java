package com.bytesmyth.graphics.font;

import com.bytesmyth.graphics.sprite.SpriteBatcher;
import com.bytesmyth.graphics.texture.Texture;

public class FontRenderer {

    private SpriteBatcher batcher;
    private Texture texture;
    private BitmapFont font;

    public FontRenderer(SpriteBatcher batcher, Texture texture, BitmapFont font) {
        this.batcher = batcher;
        this.texture = texture;
        this.font = font;
    }



}
