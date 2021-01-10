package com.bytesmyth.graphics.ui;

import com.bytesmyth.application.Input;
import com.bytesmyth.graphics.batch.QuadTextureBatcher;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GuiManager {

    private Map<String, Gui> guis = new HashMap<>();
    private List<String> enabledGuis = new LinkedList<>();

    private GuiGraphics graphics;

    public GuiManager(GuiGraphics graphics) {
        this.graphics = graphics;
    }

    public void registerGui(String name, Gui gui) {
        this.guis.put(name, gui);
    }

    public void enableGui(String name) {
        enabledGuis.add(name);
    }

    public void disableGui(String name) {
        enabledGuis.remove(name);
    }

    public void handleInput(Input input) {
        for (String enabledGui : enabledGuis) {
            Gui gui = guis.get(enabledGui);
            gui.pollInput(input, graphics.getCamera());
        }
    }

    public void render() {
        QuadTextureBatcher batcher = graphics.getBatcher();
        OrthographicCamera2D camera = graphics.getCamera();

        batcher.begin(graphics.getTexture());

        for (String enabledGui : enabledGuis) {
            Gui gui = guis.get(enabledGui);

            gui.setPosition(-camera.getWidth()/2f, camera.getHeight()/2f);
            gui.setSize(camera.getWidth(), camera.getHeight());

            gui.layout();

            gui.draw(graphics);
        }

        batcher.end();
    }

    public Gui getGui(String hud) {
        return guis.get(hud);
    }

    public boolean isEnabled(String guiId) {
        return enabledGuis.contains(guiId);
    }
}
