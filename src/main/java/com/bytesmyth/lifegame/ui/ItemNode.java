package com.bytesmyth.lifegame.ui;

import com.bytesmyth.graphics.font.BitmapFont;
import com.bytesmyth.graphics.sprite.Sprite;
import com.bytesmyth.graphics.ui.Node;
import com.bytesmyth.graphics.Graphics;
import com.bytesmyth.lifegame.domain.item.ItemSlot;
import org.joml.Vector2f;

public class ItemNode extends Node {

    private final ItemSlot itemSlot;

    private Sprite cachedSprite;
    private String cachedItemName;

    public ItemNode(ItemSlot itemSlot) {
        this.itemSlot = itemSlot;
    }

    @Override
    public void draw(Graphics g) {
        Vector2f renderPos = getGuiPosition();

        if (hasItems()) {
            String itemName = itemSlot.getItem().getName();

            if (!itemName.equals(cachedItemName)) {
                cachedItemName = itemName;
                cachedSprite = g.getSpriteRegistry().createSprite(itemName);
                cachedSprite.setSize(getWidth()-2, getHeight()-2);
            }

            g.getBatcher().begin(cachedSprite.getTexture());
            g.getBatcher().draw(cachedSprite, renderPos.x + 1, renderPos.y - getHeight() + 1);
//            g.getBatcher().draw(renderPos.x, renderPos.y, renderPos.x + getWidth(), renderPos.y - getHeight(), cachedSprite.getTextureRegion());

            float fontSize = 12;
            BitmapFont font = getGui().getTheme().getFont();

            String countText = String.valueOf(itemSlot.getCount());
            Vector2f textSize = font.getTextSize(countText, fontSize);

            float textX = renderPos.x + getWidth() - textSize.x;
            float textY = renderPos.y - getHeight() + textSize.y;

            font.drawText(countText, textX, textY, fontSize, g.getBatcher());
        }
    }

    private boolean hasItems() {
        return itemSlot != null && itemSlot.getItem() != null && itemSlot.getCount() != 0;
    }

    public ItemSlot getItemSlot() {
        return itemSlot;
    }
}
