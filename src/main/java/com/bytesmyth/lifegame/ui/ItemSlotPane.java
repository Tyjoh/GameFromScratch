package com.bytesmyth.lifegame.ui;

import com.bytesmyth.graphics.ui.*;
import com.bytesmyth.graphics.ui.positioning.RelativePositioning;
import com.bytesmyth.graphics.Graphics;
import com.bytesmyth.lifegame.domain.item.ItemSlot;
import org.joml.Vector3f;

public class ItemSlotPane extends Pane {

    private final ItemSlot itemSlot;
    private ItemNode itemNode;
    private final MousePressListener pressListener = this::onSlotPressed;

    public ItemSlotPane(ItemSlot itemSlot) {
        this.itemSlot = itemSlot;

        this.itemNode = new ItemNode(itemSlot);
        this.itemNode.setSize(this.getWidth(), this.getHeight());
        this.itemNode.setPositioning(RelativePositioning.center());
        this.addNode(itemNode);

        this.addMousePressListener(pressListener);
    }

    @Override
    public Node setSize(float width, float height) {
        super.setSize(width, height);
        itemNode.setSize(width, height);
        return this;
    }

    @Override
    public void draw(Graphics g) {
        if (isHovered()) {
            setColor(new Vector3f(0.75f, 0.75f, 0.75f));
        } else {
            setColor(new Vector3f(1,1,1));
        }

        super.draw(g);
    }

    private boolean onSlotPressed(Mouse mouse) {
        if (!mouse.isHoldingNode() || !(mouse.getHeldNode() instanceof ItemNode)) {
            return false;
        }

        ItemNode heldNode = (ItemNode) mouse.getHeldNode();
        ItemSlot heldItems = heldNode.getItemSlot();

        // if mouse itemstack is empty, transfer items from this to mouse
        if (heldItems.isEmpty()) {
            if (!itemSlot.isEmpty()) {

                if (mouse.getLeftButton().isPressed()) {
                    //pickup all items
                    itemSlot.drainTo(heldItems);
                } else if (mouse.getRightButton().isPressed()) {
                    //pickup larger half of items. ie 3 items -> pickup 2, 4 items pickup 2.
                    int amount = (itemSlot.getCount() / 2) + (itemSlot.getCount() % 2);
                    itemSlot.transferTo(heldItems, amount);
                }
            }
        } else {
            boolean canTransfer = this.itemSlot.isEmpty() || this.itemSlot.containsSameItems(heldItems);

            if (canTransfer && !itemSlot.isFull()) {
                if (mouse.getLeftButton().isPressed()) {
                    //transfer as many as possible
                    heldItems.drainTo(itemSlot);
                } else if(mouse.getRightButton().isPressed()) {
                    //transfer one
                    heldItems.transferTo(itemSlot, 1);
                }
            } else {
                if (mouse.getLeftButton().isPressed()) {
                    heldItems.swapWith(itemSlot);
                }
            }
        }

        return false;
    }
}
