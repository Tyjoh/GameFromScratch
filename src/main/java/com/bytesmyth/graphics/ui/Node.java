package com.bytesmyth.graphics.ui;

import com.bytesmyth.graphics.ui.positioning.DefaultPositioning;
import com.bytesmyth.graphics.ui.positioning.Positioning;
import com.bytesmyth.lifegame.Graphics;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {

    private Gui gui;
    private Container parent;

    private String key;

    private float x = 0;
    private float y = 0;

    private float w = 10;//use reasonable default for easy error detection.
    private float h = 10;

    private boolean hovered = false;
    private boolean pressed = false;

    private Positioning positioning = new DefaultPositioning();

    private final List<MousePressListener> pressListeners = new ArrayList<>();

    protected Node() {
    }

    public void draw(Graphics g) { }

    public void layout() { }

    public Node setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Node setSize(float width, float height) {
        this.w = width;
        this.h = height;
        return this;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public boolean contains(Vector2f guiPoint) {
        Vector2f renderPosition = getGuiPosition();
        float left = renderPosition.x;
        float right = renderPosition.x + getWidth();
        float top = renderPosition.y;
        float bottom = renderPosition.y - getHeight();

        if (guiPoint.x < left || guiPoint.x > right) {
            return false;
        }

        if (guiPoint.y > top || guiPoint.y < bottom) {
            return false;
        }

        return true;
    }

    public boolean isHovered() {
        return hovered;
    }

    Node setHovered(boolean hovered) {
        this.hovered = hovered;
        return this;
    }

    public boolean isPressed() {
        return pressed;
    }

    Node setPressed(boolean pressed) {
        this.pressed = pressed;
        return this;
    }

    Node setParent(Container parent) {
        this.parent = parent;
        return this;
    }

    public Container getParent() {
        return parent;
    }

    public Gui getGui() {
        return gui;
    }

    Node setGui(Gui gui) {
        this.gui = gui;
        if (gui != null) {
            this.gui.registerNode(this);
        }
        return this;
    }

    public Node setKey(String key) {
        this.key = key;
        if(gui != null) {
            gui.registerNode(this);
        }
        return this;
    }

    public void clearFlags() {
        this.setHovered(false);
        this.setPressed(false);
    }

    protected String getKey() {
        return key;
    }

    public Positioning getPositioning() {
        return positioning;
    }

    public Node setPositioning(Positioning positioning) {
        this.positioning = positioning;
        return this;
    }

    public Vector2f getGuiPosition() {
        Vector2f pos = new Vector2f(getX(), getY());

        if(getParent() != null) {
            pos.add(getParent().getGuiPosition());
        }

        return pos;
    }

    public Vector2f getPosition() {
        return new Vector2f(x, y);
    }

    void pollMouseEvents(Mouse mouse) {
        boolean lmbPressed = mouse.getLeftButton().isPressed();
        boolean rmbPressed = mouse.getRightButton().isPressed();

        if (this.contains(mouse.getPosition())) {
            this.hovered = true;

            if (lmbPressed || rmbPressed) {
                if (!pressed) {
                    //only notify listeners on initial press
                    notifyPressListeners(mouse);
                }
                this.pressed = true;
            } else {
//                if (pressed) {
//                    notifyReleaseListeners(mouse);
//                }
                this.pressed = false;
            }

        } else {
//                if (pressed) {
//                    notifyReleaseListeners(mouse);
//                }
            this.hovered = false;
            this.pressed = false;
        }
    }

    public void addMousePressListener(MousePressListener listener) {
        this.pressListeners.add(listener);
    }

    public void removePressListener(MousePressListener listener) {
        this.pressListeners.remove(listener);
    }

    void notifyPressListeners(Mouse mouse) {
        for (MousePressListener pressListener : this.pressListeners) {
            try {
                pressListener.onPressed(mouse);
            } catch (Exception e) {
                System.out.println("Exception in press listener");
                e.printStackTrace();
            }
        }
    }
}
