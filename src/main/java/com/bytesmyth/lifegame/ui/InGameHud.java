package com.bytesmyth.lifegame.ui;

import com.bytesmyth.graphics.ui.Gui;
import com.bytesmyth.graphics.ui.Label;
import com.bytesmyth.graphics.ui.VBox;
import com.bytesmyth.graphics.ui.positioning.RelativePositioning;
import org.joml.Vector2f;

public class InGameHud extends Gui {

    private final Label fps;
    private final Label worldMouse;
    private final Label uiMouse;

    public InGameHud() {
        VBox vBox = new VBox();
        vBox.setPositioning(RelativePositioning.topLeft(4));
        vBox.setSize(400, 400);
        this.addNode(vBox);

        fps = new Label("");
        fps.setKey("fps_label");
        vBox.addNode(fps);
        setFps(0);

        worldMouse = new Label("");
        worldMouse.setKey("fps_label");
        vBox.addNode(worldMouse);
        setWorldMousePosition(new Vector2f());

        uiMouse = new Label("");
        uiMouse.setKey("fps_label");
        vBox.addNode(uiMouse);
        setUiMousePosition(new Vector2f());
    }

    public void setFps(int currentFps) {
        fps.setText("FPS: " + currentFps);
    }

    public void setWorldMousePosition(Vector2f pos) {
        worldMouse.setText(String.format("World Mouse: (%.1f, %.1f)", pos.x, pos.y));
    }

    public void setUiMousePosition(Vector2f pos) {
        uiMouse.setText(String.format("UI Mouse: (%.1f, %.1f)", pos.x, pos.y));
    }
}
