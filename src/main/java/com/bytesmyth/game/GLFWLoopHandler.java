package com.bytesmyth.game;

import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GLFWLoopHandler {

    private long window;

    private final int tickRate;
    private final float deltaTime;

    private final TickHandler tickHandler;
    private final DrawHandler drawHandler;
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;

    private int fps;
    private GLFWKeyCallback callback;

    public GLFWLoopHandler(long window, int tickRate, TickHandler tickHandler, DrawHandler drawHandler, KeyHandler keyHandler, MouseHandler mouseHandler) {
        this.window = window;
        this.tickRate = tickRate;
        this.deltaTime = 1f / tickRate;
        this.tickHandler = tickHandler;
        this.drawHandler = drawHandler;
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
    }

    public void run() {
        glfwSetKeyCallback(window, callback = GLFWKeyCallback.create((window, key, scancode, action, mods) -> {
            if (action == GLFW_PRESS) {
                keyHandler.onKeyPressed(key);
            } else if (action == GLFW_RELEASE) {
                keyHandler.onKeyReleased(key);
            }
        }));

        glfwSetCursorPosCallback(window, GLFWCursorPosCallback.create((window, xpos, ypos) -> {
            mouseHandler.onCursorPosChanged(xpos, ypos);
        }));

        glfwSetMouseButtonCallback(window, GLFWMouseButtonCallback.create((window, button, action, mods) -> {
            if (action == GLFW_PRESS) {
                mouseHandler.onButtonPressed(button);
            } else if (action == GLFW_RELEASE) {
                mouseHandler.onButtonReleased(button);
            }
        }));

        long lastTime = System.nanoTime();
        double tickDurationNs = 1000000000f / tickRate;
        double delta = 0;

        int frames = 0;

        long fpsTimer = System.currentTimeMillis();

        glClearColor(0.1f, 0.1f, 0.1f, 0.0f);

        while (!glfwWindowShouldClose(window)) {

            long now = System.nanoTime();
            delta += (now - lastTime) / tickDurationNs;
            lastTime = now;
            while (delta >= 1) {
               tickHandler.tick(deltaTime);
               delta--;
            }

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            drawHandler.draw(deltaTime);
            glfwSwapBuffers(window); // swap the color buffers
            glfwPollEvents();

            frames ++;

            if(System.currentTimeMillis() - fpsTimer >= 1000) {
                fps = frames;
                System.out.println("FPS:" + fps);
                frames = 0;
                fpsTimer += 1000;
            }
        }
    }
}
