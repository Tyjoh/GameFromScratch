package com.bytesmyth.testgame.ui;

import com.bytesmyth.graphics.texture.TextureRegion;
import com.bytesmyth.testgame.item.ItemSlot;
import com.bytesmyth.ui.GuiGraphics;
import com.bytesmyth.ui.Pane;
import org.joml.Vector2f;

public class ItemSlotPane extends Pane {

    private ItemSlot itemSlot;

    @Override
    public void draw(GuiGraphics g) {
        super.draw(g);

        if (itemSlot != null && itemSlot.getItem() != null && itemSlot.getCount() != 0) {
            TextureRegion coin = g.getAtlas().getRegionByCoord(0, 24);
            g.getBatcher().draw(getX(), getY(), getX() + getWidth(), getY() - getHeight(), coin);

            int fontSize = 12;
            String countText = String.valueOf(itemSlot.getCount());
            Vector2f textSize = g.getFont().getTextSize(countText, fontSize);

            float textX = getX() + getWidth() - textSize.x;
            float textY = getY() - getHeight() + textSize.y;

            g.getFont().drawText(countText, textX, textY, fontSize, g.getBatcher());
        }
    }

    public ItemSlotPane setItemSlot(ItemSlot itemSlot) {
        this.itemSlot = itemSlot;
        return this;
    }
}
