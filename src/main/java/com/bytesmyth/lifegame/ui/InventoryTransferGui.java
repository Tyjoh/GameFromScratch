package com.bytesmyth.lifegame.ui;

import com.bytesmyth.application.Input;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.graphics.ui.Container;
import com.bytesmyth.graphics.ui.Gui;
import com.bytesmyth.graphics.ui.Pane;
import com.bytesmyth.graphics.ui.positioning.HorizontalAlignment;
import com.bytesmyth.graphics.ui.positioning.RelativePositioning;
import com.bytesmyth.graphics.ui.positioning.VerticalAlignment;
import com.bytesmyth.lifegame.domain.item.Inventory;
import com.bytesmyth.lifegame.domain.item.ItemSlot;

public class InventoryTransferGui extends Gui {

    private static final float CELL_SIZE = 32f;
    private static final float CELL_PADDING = 2f;

    private final int invWidth;
    private final int invHeight;

    private Container topInventoryContainer;
    private Container bottomInventoryContainer;
    private final Pane topInvPane;
    private final Pane bottomInvPane;

    public InventoryTransferGui(int invWidth, int invHeight) {
        this.invWidth = invWidth;
        this.invHeight = invHeight;

        topInvPane = new Pane();
        this.addNode(topInvPane);

        bottomInvPane = new Pane();
        this.addNode(bottomInvPane);

        ItemNode handSlot = new ItemNode(new ItemSlot(null, 0));
        handSlot.setSize(CELL_SIZE, CELL_SIZE);
        this.getMouse().setHeldNode(handSlot);
    }

    public void setInventories(Inventory top, Inventory bottom) {
        if (topInventoryContainer != null) {
            topInvPane.removeNode(topInventoryContainer);
        }

        if (bottomInventoryContainer != null) {
            bottomInvPane.removeNode(bottomInventoryContainer);
        }

        if (top != null && bottom != null) {
            topInventoryContainer = new InventoryContainer(top, invWidth, invHeight, CELL_SIZE, CELL_PADDING);
            topInvPane.setSize(topInventoryContainer.getWidth() + CELL_SIZE, topInventoryContainer.getHeight() + CELL_SIZE);
            topInvPane.setPositioning(new RelativePositioning(HorizontalAlignment.CENTER, VerticalAlignment.CENTER, 0, topInvPane.getHeight()/2f + CELL_SIZE/2f));
            topInvPane.addNode(topInventoryContainer);


            bottomInventoryContainer = new InventoryContainer(bottom, invWidth, invHeight, CELL_SIZE, CELL_PADDING);
            bottomInvPane.setSize(bottomInventoryContainer.getWidth() + CELL_SIZE, bottomInventoryContainer.getHeight() + CELL_SIZE);
            bottomInvPane.setPositioning(new RelativePositioning(HorizontalAlignment.CENTER, VerticalAlignment.CENTER, 0, -(bottomInvPane.getHeight()/2f + CELL_SIZE/2f)));
            bottomInvPane.addNode(bottomInventoryContainer);
        }
    }
}
