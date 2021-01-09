package com.bytesmyth.application;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GLFWInput implements Input {

    private final List<Button> buttons;

    private final Map<String, Button> nameToButtonMap = new HashMap<>();
    private final Map<Integer, Button> codeToButtonMap = new HashMap<>();

    private Vector2f rawMousePosition = new Vector2f();
    private Vector2f mousePosition = new Vector2f();

    private Button leftMouseButton = new Button("LMB", GLFW.GLFW_MOUSE_BUTTON_LEFT);
    private Button rightMouseButton = new Button("RMB", GLFW.GLFW_MOUSE_BUTTON_RIGHT);

    private int windowWidth;
    private int windowHeight;

    public GLFWInput() {

        buttons = Arrays.asList(
                new Button("W", GLFW.GLFW_KEY_W),
                new Button("A", GLFW.GLFW_KEY_A),
                new Button("S", GLFW.GLFW_KEY_S),
                new Button("D", GLFW.GLFW_KEY_D),
                new Button("SPACE", GLFW.GLFW_KEY_SPACE),
                new Button("E", GLFW.GLFW_KEY_E),
                new Button("SHIFT", GLFW.GLFW_KEY_LEFT_SHIFT),
                new Button("CTRL", GLFW.GLFW_KEY_LEFT_CONTROL),
                new Button("ALT", GLFW.GLFW_KEY_LEFT_ALT),
                leftMouseButton,
                rightMouseButton
        );

        for (Button button : this.buttons) {
            nameToButtonMap.put(button.getName().toLowerCase(), button);
            codeToButtonMap.put(button.getCode(), button);
        }
    }

    public void setTime(long nanos) {
        for (Button button : buttons) {
            button.setTime(nanos);
        }
    }

    public void onKeyPressed(int key) {
        if (codeToButtonMap.containsKey(key)) {
            codeToButtonMap.get(key).setPressed(true);
        } else {
            System.out.println("WARING: unhandled key code: " + key);
        }
    }

    public void onKeyReleased(int key) {
        if (codeToButtonMap.containsKey(key)) {
            codeToButtonMap.get(key).setPressed(false);
        } else {
            System.out.println("WARING: unhandled key code: " + key);
        }
    }

    public void onMouseMoved(double x, double y) {
        this.rawMousePosition.set(x, y);
        this.updateMousePosition();
    }

    public void onMouseButtonPressed(int button) {
        if (button == leftMouseButton.getCode()) {
            leftMouseButton.setPressed(true);
        } else if (button == rightMouseButton.getCode()) {
            rightMouseButton.setPressed(true);
        }
    }

    public void onMouseButtonReleased(int button) {
        if (button == leftMouseButton.getCode()) {
            leftMouseButton.setPressed(false);
        } else if (button == rightMouseButton.getCode()) {
            rightMouseButton.setPressed(false);
        }
    }

    @Override
    public Button getKey(String key) {
        String lowerCase = key.toLowerCase();
        if (nameToButtonMap.containsKey(lowerCase)) {
            return nameToButtonMap.get(lowerCase);
        } else {
            throw new IllegalArgumentException("No key binding for '" + key + "' exists");
        }
    }

    @Override
    public Vector2f getMousePosition() {
        return mousePosition;
    }

    @Override
    public Button getLeftMouseButton() {
        return leftMouseButton;
    }

    @Override
    public Button getRightMouseButton() {
        return rightMouseButton;
    }

    public void setWindowSize(int width, int height) {
        this.windowWidth = width;
        this.windowHeight = height;
        this.updateMousePosition();
    }

    private void updateMousePosition() {
        float rawX = rawMousePosition.x;
        float rawY = rawMousePosition.y;

        float adjustedX = ((rawX / windowWidth) * 2) - 1;
        float adjustedY = ((rawY / windowHeight) * 2) - 1;

        this.mousePosition.set(adjustedX, -adjustedY);
    }
}
