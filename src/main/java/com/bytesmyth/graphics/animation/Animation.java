package com.bytesmyth.graphics.animation;

public class Animation {

    private String name;
    private Frame[] frames;

    public Animation(String name, Frame[] frames) {
        this.name = name;
        this.frames = frames;
    }

    public String getName() {
        return name;
    }

    public int size() {
        return frames.length;
    }

    public Frame getFrame(int i) {
        return frames[i];
    }

    public int getDurationTicks() {
        int total = 0;
        for (Frame frame : frames) {
            total += frame.getFrameTicks();
        }
        return total;
    }

    public Frame getFrameByTick(int animationTick) {
        int currentFrameTick = 0;
        for (Frame frame : frames) {
            int nextFrameTick = currentFrameTick + frame.getFrameTicks();
            if (animationTick < nextFrameTick) {
                return frame;
            }
            currentFrameTick = nextFrameTick;
        }
        throw new IllegalArgumentException("Animation tick out of bounds: " + animationTick + ". Total tick duration: " + getDurationTicks());
    }
}
