package com.bytesmyth.testgame.ui;

import com.bytesmyth.ui.*;

public class HudGuiDecorator {

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
