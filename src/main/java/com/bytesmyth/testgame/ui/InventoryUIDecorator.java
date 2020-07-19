package com.bytesmyth.testgame.ui;

import com.bytesmyth.ui.*;

public class InventoryUIDecorator {

    public void addInventory(Gui gui, String cellPrefix, int invWidth, int invHeight) {
        Pane pane = new Pane(512, 256 + 128);
        gui.addChild(pane, RelativePositioning.center());

        float cellSize = 48f;
        float padding = 2f;

        float width = (invWidth * cellSize) + ((invWidth-1) * padding);
        float height = (invHeight * cellSize) + ((invHeight-1) * padding);

        float sx = -width / 2f + cellSize / 2f;
        float sy = height / 2f - cellSize / 2f;

        for (int x = 0; x < invWidth; x++) {
            for (int y = 0; y < invHeight; y++) {
                Pane cell = new Pane(cellSize, cellSize);
                cell.setKey(cellPrefix + (y * invWidth + invHeight));
                cell.setOpacity(0.5f);
                RelativePositioning basicPositioning = new RelativePositioning(
                        HorizontalAlignment.CENTER,
                        VerticalAlignment.CENTER,
                        sx + (x * cellSize) + (x * padding),
                        sy - (y * cellSize) - (y * padding));
                pane.addChild(cell, basicPositioning);
            }
        }
    }

}
