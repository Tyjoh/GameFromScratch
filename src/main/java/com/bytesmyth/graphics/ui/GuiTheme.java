package com.bytesmyth.graphics.ui;

import com.bytesmyth.graphics.font.BitmapFont;
import com.bytesmyth.graphics.texture.NinePatch;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.resources.Assets;

public class GuiTheme {

    private final Texture texture;
    private final BitmapFont font;
    private final NinePatch window;
    private final NinePatch button;
    private final TextureAtlas atlas;

    public GuiTheme() {
        texture = Assets.loadTexture("/textures/gui-tileset.png");
        font = Assets.loadFont("mono");
        atlas = new TextureAtlas(texture, 16, 16);

        window = new NinePatch(atlas, 0, 16, 16);
        button = new NinePatch(atlas, 3, 16, 16);
    }

    public Texture getTexture() {
        return texture;
    }

    public BitmapFont getFont() {
        return font;
    }

    public NinePatch getWindow() {
        return window;
    }

    public NinePatch getButton() {
        return button;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }
}
