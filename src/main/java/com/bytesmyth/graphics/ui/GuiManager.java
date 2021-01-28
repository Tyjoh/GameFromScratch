package com.bytesmyth.graphics.ui;

import com.bytesmyth.application.Input;
import com.bytesmyth.graphics.sprite.SpriteBatcher;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.lifegame.Graphics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GuiManager {

    private Map<String, Gui> guis = new HashMap<>();

    private Graphics graphics;

    public GuiManager(Graphics graphics) {
        this.graphics = graphics;
    }

    public void registerGui(String name, Gui gui) {
        gui.setGraphics(graphics);
        this.guis.put(name, gui);
    }

    public void enableGui(String name) {
        guis.get(name).enable();
    }

    public void disableGui(String name) {
        guis.get(name).disable();
    }

    public void handleInput(Input input) {
        for (Gui gui : guis.values()) {
            if (gui.isEnabled()) {
                gui.pollInput(input, graphics.getCamera());
            }
        }
    }

    public void render() {
        SpriteBatcher batcher = graphics.getBatcher();
        OrthographicCamera2D camera = graphics.getCamera();

        for (Gui gui : guis.values()) {
            batcher.begin(gui.getTheme().getTexture());
            if (gui.isEnabled()) {
                gui.setPosition(-camera.getWidth() / 2f, camera.getHeight() / 2f);
                gui.setSize(camera.getWidth(), camera.getHeight());

                gui.layout();

                gui.draw(graphics);
            }
            batcher.end();
        }
    }

    public Gui getGui(String key) {
        return guis.get(key);
    }

    public List<String> getEnabledGuis() {
        return guis.entrySet().stream().filter(e -> e.getValue().isEnabled()).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public boolean isEnabled(String guiId) {
        return guis.get(guiId).isEnabled();
    }
}
