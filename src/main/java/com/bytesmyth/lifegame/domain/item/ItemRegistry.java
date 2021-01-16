package com.bytesmyth.lifegame.domain.item;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry {

    private Map<String, ItemRenderer> items = new HashMap<>();

    public void register(String item, ItemRenderer renderer) {
        items.put(item.toLowerCase(), renderer);
    }

    public ItemRenderer getRenderer(String item) {
        return items.get(item.toLowerCase());
    }

}
