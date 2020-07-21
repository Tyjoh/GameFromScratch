package com.bytesmyth.testgame.ui;

import com.bytesmyth.ui.*;

public class InventoryGuiFactory {

    public Pane createInventory(String inventoryName, int invWidth, int invHeight) {
        float cellSize = 48f;
        float padding = 2f;

        float width = (invWidth * cellSize) + ((invWidth-1) * padding);
        float height = (invHeight * cellSize) + ((invHeight-1) * padding);

        float sx = -width / 2f + cellSize / 2f;
        float sy = height / 2f - cellSize / 2f;

        Pane pane = new Pane(width + cellSize, height + cellSize);
        pane.setKey(inventoryName + "_pane");

        for (int x = 0; x < invWidth; x++) {
            for (int y = 0; y < invHeight; y++) {
                Pane cell = new Pane(cellSize, cellSize);
                cell.setKey(inventoryName + (y * invWidth + invHeight));
                cell.setOpacity(0.5f);
                RelativePositioning basicPositioning = new RelativePositioning(
                        HorizontalAlignment.CENTER,
                        VerticalAlignment.CENTER,
                        sx + (x * cellSize) + (x * padding),
                        sy - (y * cellSize) - (y * padding));
                pane.addChild(cell, basicPositioning);
            }
        }

        return pane;
    }

}
