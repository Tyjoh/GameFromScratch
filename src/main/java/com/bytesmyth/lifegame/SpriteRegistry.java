package com.bytesmyth.lifegame;

import com.bytesmyth.graphics.sprite.Sprite;

import java.util.HashMap;
import java.util.Map;

public class SpriteRegistry {

    public interface SpriteFactory {
        Sprite get();
    }

    private Map<String, SpriteFactory> spriteFactories = new HashMap<>();

    public void registerStaticSprite(String id, Sprite sprite) {
        spriteFactories.put(id, () -> sprite);
    }

    public void registerDynamicSprite(String id, SpriteFactory factory) {
        spriteFactories.put(id, factory);
    }

    public Sprite getSprite(String id) {
        return spriteFactories.get(id).get();
    }

}
