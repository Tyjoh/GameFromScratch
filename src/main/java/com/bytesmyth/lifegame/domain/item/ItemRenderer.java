package com.bytesmyth.lifegame.domain.item;

import com.bytesmyth.graphics.texture.TextureRegion;

public interface ItemRenderer {
    TextureRegion render(Item item);
}
