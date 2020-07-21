package com.bytesmyth.testgame.ui;

import com.bytesmyth.graphics.batch.QuadTextureBatcher;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.ui.*;

public class GuiFactory {

    private Texture guiTexture;
    private QuadTextureBatcher batcher;
    private OrthographicCamera2D uiCamera;

    public GuiFactory(Texture guiTexture, QuadTextureBatcher batcher, OrthographicCamera2D uiCamera) {
        this.guiTexture = guiTexture;
        this.batcher = batcher;
        this.uiCamera = uiCamera;
    }

    public Gui createHUDGui(){
        Gui gui = newGui();

        HudGuiDecorator hudDecorator = new HudGuiDecorator();
        hudDecorator.addFpsDisplay(gui);
        hudDecorator.addUIMousePositionDisplay(gui);
        hudDecorator.addWorldMousePositionDisplay(gui);

        return gui;
    }

    public Gui createPlayerInventoryGui() {
        Gui gui = newGui();
        InventoryGuiFactory inventoryGuiFactory = new InventoryGuiFactory();
        Pane invPane = inventoryGuiFactory.createInventory("player_inventory", 5, 3);

        Label title = new Label("Player Inventory");
        invPane.addChild(title, new RelativePositioning(
                HorizontalAlignment.CENTER,
                VerticalAlignment.TOP,
                0,
                -8
        ));

        gui.addChild(invPane, RelativePositioning.center());
        return gui;
    }

    public Gui createInventoryTransferGui() {
        Gui gui = newGui();
        InventoryGuiFactory inventoryGuiFactory = new InventoryGuiFactory();

        Pane invPane = inventoryGuiFactory.createInventory("player_inventory", 5, 3);
        Label title = new Label("Player Inventory");
        invPane.addChild(title, new RelativePositioning(
                HorizontalAlignment.CENTER,
                VerticalAlignment.TOP,
                0,
                -8
        ));
        gui.addChild(invPane, RelativePositioning.center(0, invPane.getHeight()/2f + 8));

        Pane otherInv = inventoryGuiFactory.createInventory("other_inventory", 5, 3);
        Label otherTitle = new Label("Other Inventory");
        otherInv.addChild(otherTitle, new RelativePositioning(
                HorizontalAlignment.CENTER,
                VerticalAlignment.BOTTOM,
                0,
                -8
        ));

        gui.addChild(otherInv, RelativePositioning.center(0, -invPane.getHeight()/2f - 8));

        return gui;
    }

    private Gui newGui() {
        return new Gui(guiTexture, batcher, uiCamera);
    }

}
