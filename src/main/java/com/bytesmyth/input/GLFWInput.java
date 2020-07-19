package com.bytesmyth.input;

import com.bytesmyth.game.KeyHandler;
import com.bytesmyth.game.MouseHandler;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class GLFWInput implements KeyHandler, MouseHandler, Input {

    private boolean leftMouseDown;
    private boolean rightMouseDown;

    private Map<String, Boolean> keyDownMap = new HashMap<>();
    private Map<String, Integer> keyToCodeMap = new HashMap<>();
    private Map<Integer, String> codeToKeyMap = new HashMap<>();

    private Vector2f mousePosition = new Vector2f();

    public GLFWInput() {
        keyToCodeMap.put("w", GLFW.GLFW_KEY_W);
        keyToCodeMap.put("a", GLFW.GLFW_KEY_A);
        keyToCodeMap.put("s", GLFW.GLFW_KEY_S);
        keyToCodeMap.put("d", GLFW.GLFW_KEY_D);
        keyToCodeMap.put("space", GLFW.GLFW_KEY_SPACE);
        keyToCodeMap.put("e", GLFW.GLFW_KEY_E);
        keyToCodeMap.put("shift", GLFW.GLFW_KEY_LEFT_SHIFT);

        keyToCodeMap.forEach((key, value) -> codeToKeyMap.put(value, key));
    }

    @Override
    public void onKeyPressed(int key) {
        if (codeToKeyMap.containsKey(key)) {
            keyDownMap.put(codeToKeyMap.get(key), true);
        } else {
            System.out.println("WARING: unhandled key code: " + key);
        }
    }

    @Override
    public void onKeyReleased(int key) {
        if (codeToKeyMap.containsKey(key)) {
            keyDownMap.put(codeToKeyMap.get(key), false);
        } else {
            System.out.println("WARING: unhandled key code: " + key);
        }
    }

    @Override
    public void onCursorPosChanged(double x, double y) {
        this.mousePosition.set(x, y);
    }

    @Override
    public void onButtonPressed(int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            leftMouseDown = true;
        } else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            rightMouseDown = true;
        }
    }

    @Override
    public void onButtonReleased(int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            leftMouseDown = false;
        } else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            rightMouseDown = false;
        }
    }

    @Override
    public boolean isKeyDown(String key) {
        return keyDownMap.containsKey(key.toLowerCase()) &&  keyDownMap.get(key.toLowerCase());
    }

    @Override
    public boolean isMouseDown(String button) {
        if (button.equalsIgnoreCase("left")) {
            return leftMouseDown;
        } else if (button.equalsIgnoreCase("right")) {
            return rightMouseDown;
        } else {
            System.out.println("WARNING: unhandled mouse button: " + button);
            return false;
        }
    }

    @Override
    public Vector2f getMousePosition() {
        return mousePosition;
    }
}
