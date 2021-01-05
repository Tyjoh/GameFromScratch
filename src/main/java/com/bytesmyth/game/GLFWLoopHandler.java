package com.bytesmyth.game;

import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GLFWLoopHandler {

    private long window;

    private final int ticksPerSecond;
    private final int millisPerTick;
    private final int maxFrameSkip = 10;

    private final TickHandler tickHandler;
    private final DrawHandler drawHandler;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private WindowSizeListener windowSizeListener = null;

    private int fps;

    public GLFWLoopHandler(long window, int ticksPerSecond, TickHandler tickHandler, DrawHandler drawHandler, KeyHandler keyHandler, MouseHandler mouseHandler) {
        this.window = window;
        this.ticksPerSecond = ticksPerSecond;
        this.millisPerTick = 1000 / ticksPerSecond;

        this.tickHandler = tickHandler;
        this.drawHandler = drawHandler;
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
    }

    public void run() {
        glfwSetKeyCallback(window, GLFWKeyCallback.create((window, key, scancode, action, mods) -> {
            if (keyHandler == null) {
                return;
            }

            if (action == GLFW_PRESS) {
                keyHandler.onKeyPressed(key);
            } else if (action == GLFW_RELEASE) {
                keyHandler.onKeyReleased(key);
            }
        }));

        glfwSetCursorPosCallback(window, GLFWCursorPosCallback.create((window, xpos, ypos) -> {
            if (mouseHandler != null) {
                mouseHandler.onCursorPosChanged(xpos, ypos);
            }
        }));

        glfwSetMouseButtonCallback(window, GLFWMouseButtonCallback.create((window, button, action, mods) -> {
            if (mouseHandler == null) {
                return;
            }

            if (action == GLFW_PRESS) {
                mouseHandler.onButtonPressed(button);
            } else if (action == GLFW_RELEASE) {
                mouseHandler.onButtonReleased(button);
            }
        }));

        glfwSetWindowSizeCallback(window, GLFWWindowSizeCallback.create((window, width, height) -> {
            if (windowSizeListener != null) {
                glViewport(0, 0, width, height);
                windowSizeListener.onWindowSizeChanged(width, height);
            }
        }));

        long nextTick = now();

        int frames = 0;

        long fpsTimer = System.currentTimeMillis();

        glClearColor(0.1f, 0.1f, 0.1f, 0.0f);

        while (!glfwWindowShouldClose(window)) {

            int loops = 0;
            while(now() > nextTick && loops < maxFrameSkip) {
                tickHandler.tick(1f/ticksPerSecond);

                nextTick += millisPerTick;
                loops++;
            }

            float alpha = (now() + millisPerTick - nextTick) / (float)millisPerTick;

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            drawHandler.draw(alpha);
            glfwSwapBuffers(window); // swap the color buffers
            glfwPollEvents();

            frames ++;

            if(System.currentTimeMillis() - fpsTimer >= 1000) {
                fps = frames;
                drawHandler.setFps(fps);
                frames = 0;
                fpsTimer += 1000;
            }
        }
    }

    public long now() {
        return System.nanoTime() / 1_000_000;
    }

    public GLFWLoopHandler setWindowSizeListener(WindowSizeListener windowSizeListener) {
        this.windowSizeListener = windowSizeListener;
        return this;
    }

    public GLFWLoopHandler setKeyHandler(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        return this;
    }

    public GLFWLoopHandler setMouseHandler(MouseHandler mouseHandler) {
        this.mouseHandler = mouseHandler;
        return this;
    }
}
