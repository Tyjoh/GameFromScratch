package com.bytesmyth.lifegame.ui;

import com.bytesmyth.application.GameContext;
import com.bytesmyth.graphics.ui.Gui;
import com.bytesmyth.graphics.ui.Label;
import com.bytesmyth.graphics.ui.VBox;
import com.bytesmyth.graphics.ui.positioning.RelativePositioning;
import com.bytesmyth.lifegame.LifeGame;
import com.bytesmyth.util.Provider;
import org.joml.Vector2f;

public class InGameHud extends Gui {

    private final Label fps;
    private final Label worldMouse;
    private final Label uiMouse;

    private final LifeGame game;
    private final GameContext gameContext;

    public InGameHud(LifeGame game, GameContext gameContext) {
        this.game = game;
        this.gameContext = gameContext;

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

    @Override
    public void tick(float dt) {
        setUiMousePosition(game.getUiGraphics().toCameraCoordinates(gameContext.getInput().getMousePosition()));
        setWorldMousePosition(game.getWorldGraphics().toCameraCoordinates(gameContext.getInput().getMousePosition()));
        setFps(gameContext.getFps());
    }

    private void setFps(int currentFps) {
        fps.setText("FPS: " + currentFps);
    }

    private void setWorldMousePosition(Vector2f pos) {
        worldMouse.setText(String.format("World Mouse: (%.1f, %.1f)", pos.x, pos.y));
    }

    private void setUiMousePosition(Vector2f pos) {
        uiMouse.setText(String.format("UI Mouse: (%.1f, %.1f)", pos.x, pos.y));
    }
}
