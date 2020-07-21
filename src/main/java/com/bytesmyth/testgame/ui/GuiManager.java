package com.bytesmyth.testgame.ui;

import com.bytesmyth.input.Input;
import com.bytesmyth.ui.Gui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GuiManager {

    private Map<String, Gui> guis = new HashMap<>();
    private List<String> enabledGuis = new LinkedList<>();

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
            gui.handleGuiInput(input);
        }
    }

    public void render() {
        for (String enabledGui : enabledGuis) {
            Gui gui = guis.get(enabledGui);
            gui.render();
        }
    }

    public Gui getGui(String hud) {
        return guis.get(hud);
    }
}
