package com.bytesmyth.lifegame.ui;

import com.bytesmyth.graphics.texture.TextureRegion;
import com.bytesmyth.graphics.ui.GuiGraphics;
import com.bytesmyth.graphics.ui.Node;
import com.bytesmyth.lifegame.domain.item.ItemRenderer;
import com.bytesmyth.lifegame.domain.item.ItemSlot;
import org.joml.Vector2f;

public class ItemNode extends Node {

    private final ItemSlot itemSlot;

    public ItemNode(ItemSlot itemSlot) {
        this.itemSlot = itemSlot;
    }

    @Override
    public void draw(GuiGraphics g) {
        Vector2f renderPos = getGuiPosition();

        if (hasItems()) {
            ItemRenderer renderer = g.getItemRegistry().getRenderer(itemSlot.getItem().getName());
            TextureRegion textureRegion = renderer.render(itemSlot.getItem());
            g.getBatcher().draw(renderPos.x, renderPos.y, renderPos.x + getWidth(), renderPos.y - getHeight(), textureRegion);

            int fontSize = 12;
            String countText = String.valueOf(itemSlot.getCount());
            Vector2f textSize = g.getFont().getTextSize(countText, fontSize);

            float textX = renderPos.x + getWidth() - textSize.x;
            float textY = renderPos.y - getHeight() + textSize.y;

            g.getFont().drawText(countText, textX, textY, fontSize, g.getBatcher());
        }
    }

    private boolean hasItems() {
        return itemSlot != null && itemSlot.getItem() != null && itemSlot.getCount() != 0;
    }

    public ItemSlot getItemSlot() {
        return itemSlot;
    }
}
