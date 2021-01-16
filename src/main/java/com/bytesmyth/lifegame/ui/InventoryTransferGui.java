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

    private final Container topSlotContainer;
    private final Container bottomSlotContainer;

    public InventoryTransferGui(int invWidth, int invHeight) {
        this.invWidth = invWidth;
        this.invHeight = invHeight;

        float width = (invWidth * CELL_SIZE) + ((invWidth-1) * CELL_PADDING);
        float height = (invHeight * CELL_SIZE) + ((invHeight-1) * CELL_PADDING);

        Pane topInvPane = new Pane();
        topInvPane.setSize(width + CELL_SIZE, height + CELL_SIZE);
        topInvPane.setPositioning(new RelativePositioning(HorizontalAlignment.CENTER, VerticalAlignment.CENTER, 0, height/2f + CELL_SIZE/2f));
        this.addNode(topInvPane);

        Pane bottomInvPane = new Pane();
        bottomInvPane.setSize(width + CELL_SIZE, height + CELL_SIZE);
        bottomInvPane.setPositioning(new RelativePositioning(HorizontalAlignment.CENTER, VerticalAlignment.CENTER, 0, -(height/2f + CELL_SIZE/2f)));
        this.addNode(bottomInvPane);

        topSlotContainer = new Container();
        topSlotContainer.setPositioning(RelativePositioning.center());
        topSlotContainer.setSize(width, height);
        topInvPane.addNode(topSlotContainer);

        bottomSlotContainer = new Container();
        bottomSlotContainer.setPositioning(RelativePositioning.center());
        bottomSlotContainer.setSize(width, height);
        bottomInvPane.addNode(bottomSlotContainer);

        ItemNode handSlot = new ItemNode(new ItemSlot(null, 0));
        handSlot.setSize(CELL_SIZE, CELL_SIZE);
        this.getMouse().setHeldNode(handSlot);
    }

    public void setInventories(Inventory top, Inventory bottom) {
        topSlotContainer.clearNodes();
        bottomSlotContainer.clearNodes();
        if (top != null && bottom != null) {
            initialize(topSlotContainer, top);
            initialize(bottomSlotContainer, bottom);
        }
    }

    private void initialize(Container container, Inventory inventory) {
        int slotNum = 0;
        for (int y = 0; y < invHeight; y++) {
            for (int x = 0; x < invWidth; x++) {
                ItemSlot itemSlot = inventory.getSlot(slotNum);

                ItemSlotPane cell = new ItemSlotPane(itemSlot);
                cell.setPosition((x * CELL_SIZE) + (x * CELL_PADDING), -(y * CELL_SIZE) - (y * CELL_PADDING));
                cell.setSize(CELL_SIZE, CELL_SIZE);
                cell.setOpacity(0.5f);

                container.addNode(cell);

                slotNum++;
            }
        }
    }

    @Override
    public void pollInput(Input input, OrthographicCamera2D camera) {
        super.pollInput(input, camera);

        if (input.getKey("E").isJustPressed()) {
            this.disable();
        }
    }
}
