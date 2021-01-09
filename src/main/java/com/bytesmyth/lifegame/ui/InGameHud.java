package com.bytesmyth.lifegame.ui;

import com.bytesmyth.graphics.ui.*;
import org.joml.Vector2f;

public class InGameHud extends Gui {

    private final Label fps;
    private final Label worldMouse;
    private final Label uiMouse;

    public InGameHud() {
        Decorator decorator = new Decorator();
        fps = decorator.addFpsDisplay(this);
        worldMouse = decorator.addWorldMousePositionDisplay(this);
        uiMouse = decorator.addUIMousePositionDisplay(this);
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

    public static class Decorator {
        private float y = 4;

        public Label addFpsDisplay(Gui gui) {
            Label label = new Label("FPS:");
            label.setKey("fps_label");
            add(gui, label);
            return label;
        }

        public Label addWorldMousePositionDisplay(Gui gui) {
            Label label = new Label("Mouse: ");
            label.setKey("world_mouse_position_label");
            label.setPositioning(new RelativePositioning(HorizontalAlignment.LEFT, VerticalAlignment.TOP, 4, y));
            gui.addChild(label);
            add(gui, label);
            return label;
        }

        public Label addUIMousePositionDisplay(Gui gui) {
            Label label = new Label("Mouse: ");
            label.setKey("ui_mouse_position_label");
            add(gui, label);
            return label;
        }

        private void add(Gui gui, Node node) {
            node.setPositioning(new RelativePositioning(HorizontalAlignment.LEFT, VerticalAlignment.TOP, 4, y));
            gui.addChild(node);
            y += 24f;
        }
    }
}
