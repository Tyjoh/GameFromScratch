package com.bytesmyth.application;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GameWindow {

    private static final int MAX_FRAME_SKIP = 10;
    private static final int TICKS_PER_SECOND = 30;
    private static final int MILLIS_PER_TICK = 1000 / TICKS_PER_SECOND;

    // The window handle
    private final GameContext context = new GameContext();
    private Game game;
    private GLFWInput input;

    public GameContext init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);

        // Create the window
        long window = glfwCreateWindow(1920, 1080, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        this.context.setWindowHandle(window);

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            context.setWidth(pWidth.get(0));
            context.setHeight(pHeight.get(0));

            System.out.printf("Initial window size: %d x %d\n", context.getWidth(), context.getHeight());
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);

        // Enable v-sync
        glfwSwapInterval(0);

        // Make the window visible
        glfwShowWindow(window);

        input = new GLFWInput();

        return context;
    }

    public void run(Game game) {
        this.game = game;

        GL.createCapabilities();

        context.setInput(input);

        glfwSetKeyCallback(context.getWindowHandle(), GLFWKeyCallback.create((window, key, scancode, action, mods) -> {
            if (action == GLFW_PRESS || action == GLFW_REPEAT) {
                input.onKeyPressed(key);
            } else if (action == GLFW_RELEASE) {
                input.onKeyReleased(key);
            }
        }));

        glfwSetCursorPosCallback(context.getWindowHandle(), GLFWCursorPosCallback.create((window, x, y) -> {
            input.onMouseMoved(x, y);
        }));

        glfwSetMouseButtonCallback(context.getWindowHandle(), GLFWMouseButtonCallback.create((window, button, action, mods) -> {
            if (action == GLFW_PRESS || action == GLFW_REPEAT) {
                input.onMouseButtonPressed(button);
            } else if (action == GLFW_RELEASE) {
                input.onMouseButtonReleased(button);
            }
        }));

        glfwSetWindowSizeCallback(context.getWindowHandle(), GLFWWindowSizeCallback.create((window, width, height) -> {
            glViewport(0, 0, width, height);
            input.setWindowSize(width, height);
            context.setWidth(width);
            context.setHeight(height);
            game.onWindowResized(width, height);
        }));

        this.game.init();
        glfwMaximizeWindow(context.getWindowHandle());
//        this.game.onWindowResized(context.getWidth(), context.getHeight());

        loop();

        game.dispose();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(context.getWindowHandle());
        glfwDestroyWindow(context.getWindowHandle());

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void loop() {
        long nextTick = nowMillis();
        int frames = 0;
        long fpsTimer = nowMillis();
        float delta = 1f / TICKS_PER_SECOND;

        glClearColor(0.1f, 0.1f, 0.1f, 0.0f);

        while (!glfwWindowShouldClose(context.getWindowHandle())) {

            int loops = 0;
            while(nowMillis() > nextTick && loops < MAX_FRAME_SKIP) {
                game.tick(delta);

                nextTick += MILLIS_PER_TICK;
                loops++;
            }

            float alpha = (nowMillis() + MILLIS_PER_TICK - nextTick) / (float)MILLIS_PER_TICK;

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            game.render(alpha);
            glfwSwapBuffers(context.getWindowHandle());

            input.setTime(System.nanoTime());
            glfwPollEvents();

            frames ++;

            if(nowMillis() - fpsTimer >= 1000) {
                context.setFps(frames);
                frames = 0;
                fpsTimer += 1000;
            }
        }
    }

    public long nowMillis() {
        return System.nanoTime() / 1_000_000;
    }

}
