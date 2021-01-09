package com.bytesmyth.application;

public interface Game {

    void init();

    /**
     * Handles main game logic.
     * @param delta fraction of a second that one tick represents.
     */
    void tick(float delta);

    /**
     * Called as fast as possible. Each frame should be interpolated from the previous state to the current state.
     * @param alpha value between 0 and 1 used to interpolate between the previous and current state.
     */
    void render(float alpha);

    void onWindowResized(int width, int height);

    void dispose();
}
