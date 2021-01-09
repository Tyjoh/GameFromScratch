package com.bytesmyth.graphics.ui;

import com.bytesmyth.graphics.batch.QuadTextureBatcher;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.graphics.font.BitmapFont;
import com.bytesmyth.graphics.texture.NinePatch;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureAtlas;
import org.joml.Vector2f;

public class GuiGraphics {

    private final OrthographicCamera2D camera;
    private final QuadTextureBatcher batcher;
    private final Texture texture;

    private final TextureAtlas atlas;
    private final BitmapFont font;
    private final NinePatch windowPatch;
    private final NinePatch buttonPatch;

    public GuiGraphics(OrthographicCamera2D camera, QuadTextureBatcher batcher, Texture texture) {
        this.camera = camera;
        this.batcher = batcher;
        this.texture = texture;
        this.atlas = new TextureAtlas(texture, 16, 16);
        this.font = new BitmapFont(texture, "mono");

        windowPatch = new NinePatch(atlas, 0,16);
        buttonPatch = new NinePatch(atlas, 3,16);
    }

    public QuadTextureBatcher getBatcher() {
        return batcher;
    }

    public Texture getTexture() {
        return texture;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public NinePatch getWindowPatch() {
        return windowPatch;
    }

    public NinePatch getButtonPatch() {
        return buttonPatch;
    }

    private void drawLabel(Label label) {
        Vector2f size = font.getTextSize(label.getText(), 16f);
        float labelFontSize = 16;
        font.drawText(label.getText(), label.getX() + label.getWidth()/2f - size.x/2f, label.getY() - label.getHeight()/2f + size.y / 2f, labelFontSize, batcher);
    }

    private void drawPane(Pane pane) {
        batcher.setColor(1,1,1, pane.getOpacity());
        windowPatch.draw(pane.getX(), pane.getY(), pane.getWidth(), pane.getHeight(), batcher);
        batcher.setColor(1,1,1,1);
    }

    public OrthographicCamera2D getCamera() {
        return camera;
    }

    public BitmapFont getFont() {
        return font;
    }

}
