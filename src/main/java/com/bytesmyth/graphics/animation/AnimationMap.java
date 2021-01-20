package com.bytesmyth.graphics.animation;

import java.util.HashMap;

public class AnimationMap {

    private HashMap<String, Animation> animations = new HashMap<>();

    public AnimationMap(Animation... animations) {
        for (Animation timeline : animations) {
            this.animations.put(timeline.getName().toLowerCase(), timeline);
        }
    }

    public Animation getAnimation(String name) {
        return animations.get(name.toLowerCase());
    }

    public boolean hasAnimation(String name) {
        return animations.containsKey(name.toLowerCase());
    }
}
