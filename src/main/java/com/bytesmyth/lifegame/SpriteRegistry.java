package com.bytesmyth.lifegame;

import com.bytesmyth.graphics.sprite.Sprite;

import java.util.HashMap;
import java.util.Map;

public class SpriteRegistry {

    public interface SpriteFactory {
        Sprite get();
    }

    private Map<String, SpriteFactory> factories = new HashMap<>();

    public void registerSprite(String id, SpriteFactory factory) {
        factories.put(id, factory);
    }

    public Sprite createSprite(String id) {
        return factories.get(id).get();
    }

}
