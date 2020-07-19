package com.bytesmyth.ui;

import com.bytesmyth.graphics.batch.QuadTextureBatcher;
import com.bytesmyth.graphics.font.BitmapFont;
import com.bytesmyth.graphics.texture.NinePatch;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.input.Input;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Gui {

    private QuadTextureBatcher batcher;
    private boolean enabled = true;

    private final Texture guiTexture;
    private final TextureAtlas atlas;

    private final NinePatch windowPatch;
    private final NinePatch buttonPatch;

    private final BitmapFont font;

    private Pane rootNode;

    public Gui(Pane rootNode, Texture guiTexture, QuadTextureBatcher batcher) {
        this.rootNode = rootNode;
        this.guiTexture = guiTexture;
        this.batcher = batcher;

        atlas = new TextureAtlas(guiTexture, 16, 16);
        windowPatch = new NinePatch(atlas, 0,16);
        buttonPatch = new NinePatch(atlas, 3,16);
        font = new BitmapFont(guiTexture, "mono");
    }

    public void handleGuiInput(Input input) {
        if (!enabled) {
            return;
        }

//        System.out.println("Handling gui input");
    }

    public void render() {
        if (!enabled) {
            return;
        }

        batcher.begin(guiTexture);
        drawNode(0, 0, rootNode);
        batcher.end();
    }

    private void drawNode(float x, float y, Node node) {
        if (node instanceof Pane) {
            drawPane(x, y, (Pane) node);
        } else if (node instanceof Button) {
            drawButton(x, y, (Button) node);
        }
    }

    private void drawPane(float x, float y, Pane pane) {
        float paneHW = pane.getW() / 2f;
        float paneHH = pane.getH() / 2f;

        windowPatch.draw(x - paneHW, y + paneHH, pane.getW(), pane.getH(), batcher);

        for (Node child : pane.getChildren()) {
            float childHW = child.getW() / 2f;
            float childHH = child.getH() / 2f;

            RelativePositioning positioning = pane.getNodePositioning(child);
            float horizontalOffset = positioning.getHorizontalOffset();

            float targetX = x;
            float targetY = y;

            switch (positioning.getHorizontalAlignment()) {
                case LEFT:
                    if (horizontalOffset >= 0) {
                        targetX = x - paneHW + childHW + horizontalOffset;
                    } else {
                        targetX = x - paneHW - childHW + horizontalOffset;
                    }
                    break;
                case RIGHT:
                    if (horizontalOffset >= 0) {
                        targetX = x + paneHW - childHW - horizontalOffset;
                    } else {
                        targetX = x + paneHW + childHW - horizontalOffset;
                    }
                    break;
                case CENTER:
                    targetX = x + horizontalOffset;
                    break;
            }

            float verticalOffset = positioning.getVerticalOffset();

            switch (positioning.getVerticalAlignment()) {
                case BOTTOM:
                    if (verticalOffset >= 0) {
                        targetY = y - paneHH + childHH + verticalOffset;
                    } else {
                        targetY = y - paneHH - childHH + verticalOffset;
                    }
                    break;
                case TOP:
                    if (verticalOffset >= 0) {
                        targetY = y + paneHH - childHH - verticalOffset;
                    } else {
                        targetY = y + paneHH + childHH - verticalOffset;
                    }
                    break;
                case CENTER:
                    targetY = y + verticalOffset;
                    break;
            }

            drawNode(targetX, targetY, child);
        }
    }

    private void drawButton(float x, float y, Button button) {
        Vector3f color = button.getColor();

        float hw = button.getW() / 2f;
        float hh = button.getH() / 2f;
        batcher.setColor(color.x, color.y, color.z, 1f);
        buttonPatch.draw(x - hw, y + hh, button.getW(), button.getH(), batcher);

        Vector3f textColor = button.getTextColor();
        batcher.setColor(textColor.x, textColor.y, textColor.z, 1f);
        //TODO: position text properly
        int buttonFontSize = 16;
        Vector2f size = font.getTextSize(button.getText(), buttonFontSize);
        font.drawText(button.getText(), x - size.x/2f, y + size.y / 2f, buttonFontSize, batcher);

        batcher.setColor(1,1,1,1);
    }

}
