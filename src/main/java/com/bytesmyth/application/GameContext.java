package com.bytesmyth.application;

public class GameContext {

    private long windowHandle;

    private Input input;

    private int width;
    private int height;
    private int fps;

    public Input getInput() {
        return input;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    long getWindowHandle() {
        return windowHandle;
    }

    GameContext setInput(Input input) {
        this.input = input;
        return this;
    }

    GameContext setWidth(int width) {
        this.width = width;
        return this;
    }

    GameContext setHeight(int height) {
        this.height = height;
        return this;
    }

    GameContext setWindowHandle(long windowHandle) {
        this.windowHandle = windowHandle;
        return this;
    }

    public int getFps() {
        return fps;
    }

    void setFps(int fps) {
        this.fps = fps;
    }
}
