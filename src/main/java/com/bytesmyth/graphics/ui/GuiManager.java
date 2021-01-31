package com.bytesmyth.graphics.ui;

import com.bytesmyth.application.Input;
import com.bytesmyth.graphics.sprite.SpriteBatcher;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.graphics.Graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GuiManager {

    private Map<String, Gui> guis = new HashMap<>();
    private Map<String, String> guiToGroup = new HashMap<>();

    private Map<String, List<String>> guiGroups = new HashMap<>();

    private Graphics graphics;

    public GuiManager(Graphics graphics) {
        this.graphics = graphics;
    }

    public void registerGui(String name, String group, Gui gui) {
        gui.setGraphics(graphics);
        gui.setGuiManager(this);
        this.guis.put(name, gui);
        this.guiToGroup.put(name, group);

        if (!this.guiGroups.containsKey(group)) {
            this.guiGroups.put(group, new ArrayList<>());
        }
        this.guiGroups.get(group).add(name);
    }

    public boolean groupActive(String group) {
        return guiGroups.get(group).stream().anyMatch(gui -> guis.get(gui).isEnabled());
    }

    public void enableGui(String name) {
        guis.get(name).enable();
    }

    public void disableGui(String name) {
        guis.get(name).disable();
    }

    public void poll(Input input) {
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

    public void tick(float dt) {
        for (Gui gui : guis.values()) {
            if (gui.isEnabled()) {
                gui.tick(dt);
            }
        }
    }

    public Gui getGui(String key) {
        return guis.get(key);
    }

    public boolean isEnabled(String guiId) {
        return guis.get(guiId).isEnabled();
    }

    public void disableGroup(String playerGroup) {
        for (String gui : guiGroups.get(playerGroup)) {
            disableGui(gui);
        }
    }
}
