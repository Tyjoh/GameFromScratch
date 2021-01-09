package com.bytesmyth.testgame.ui;

import com.bytesmyth.ui.Gui;
import com.bytesmyth.ui.Label;
import org.joml.Vector2f;

public class InGameHud extends Gui {

    private final Label fps;
    private final Label worldMouse;
    private final Label uiMouse;

    public InGameHud() {
        HudGuiDecorator hudGuiDecorator = new HudGuiDecorator();
        fps = hudGuiDecorator.addFpsDisplay(this);
        worldMouse = hudGuiDecorator.addWorldMousePositionDisplay(this);
        uiMouse = hudGuiDecorator.addUIMousePositionDisplay(this);
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
