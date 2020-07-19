package com.bytesmyth.graphics.animation;

public class AnimationTimeline {

    private String name;
    private int[] frames;

    public AnimationTimeline(String name, int[] frames) {
        this.name = name;
        this.frames = frames;
    }

    public String getName() {
        return name;
    }

    public int size() {
        return frames.length;
    }

    public int getFrame(int i) {
        return frames[i];
    }
}
