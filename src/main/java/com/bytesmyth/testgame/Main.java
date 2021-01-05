package com.bytesmyth.testgame;

import com.bytesmyth.editor.Editor;
import com.bytesmyth.game.GLFWLoopHandler;
import com.bytesmyth.input.GLFWInput;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {

    // The window handle
    private long window;

    private String mode;

    private int initialWidth;
    private int initialHeight;

    public Main(String mode) {
        this.mode = mode;
    }

    public void run() {
        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
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
        window = glfwCreateWindow(1280, 800, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwMaximizeWindow(window);
            glfwGetWindowSize(window, pWidth, pHeight);

            initialWidth = pWidth.get(0);
            initialHeight = pHeight.get(0);
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);

        // Enable v-sync
        glfwSwapInterval(0);

        // Make the window visible
        glfwShowWindow(window);

    }

    private void loop() {
        GL.createCapabilities();
        GLFWInput input = new GLFWInput();

        GLFWLoopHandler looper;
        if(mode.equalsIgnoreCase("game")) {
            Game game = new Game(input);
            looper = new GLFWLoopHandler(window, 30, game, game, input, input);

            looper.setWindowSizeListener((width, height) -> {
                input.onWindowSizeChanged(width, height);
                game.onWindowSizeChanged(width, height);
            });

            input.onWindowSizeChanged(initialWidth, initialHeight);
            game.onWindowSizeChanged(initialWidth, initialHeight);
        } else if (mode.equalsIgnoreCase("editor")) {
            Editor editor = new Editor(input);
            looper = new GLFWLoopHandler(window, 60, editor, editor, input, input);
        } else {
            throw new IllegalArgumentException(mode);
        }
        looper.run();
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            new Main(args[0]).run();
        } else {
            new Main("game").run();
        }
    }

}
