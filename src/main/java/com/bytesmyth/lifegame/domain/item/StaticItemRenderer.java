package com.bytesmyth.lifegame.domain.item;

import com.bytesmyth.graphics.texture.TextureRegion;

public class StaticItemRenderer implements ItemRenderer {

    private TextureRegion region;

    public StaticItemRenderer(TextureRegion region) {
        this.region = region;
    }

    @Override
    public TextureRegion render(Item item) {
        return region;
    }
}
