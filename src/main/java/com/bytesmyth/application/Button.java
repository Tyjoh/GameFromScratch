package com.bytesmyth.application;

public class Button {

    private static final long NANOS_PER_MILLI = 1_000_000;

    private String name;
    private int code;

    private boolean pressed;
    private long initialPressTimestamp;
    private long lastPressTimestamp;

    public Button(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    void setPressed(boolean pressed) {
        if (pressed) {
            if (!this.pressed) {
                initialPressTimestamp = System.nanoTime();
            }
            lastPressTimestamp = System.nanoTime();
        }
        this.pressed = pressed;
    }

    public boolean isPressed() {
        return pressed;
    }

    public boolean isJustPressed() {
        return pressed && initialPressTimestamp == lastPressTimestamp;
    }

    public long getMillisSinceLastPress() {
        return (System.nanoTime() - lastPressTimestamp) / NANOS_PER_MILLI;
    }

    public long getPressDurationMillis() {
        return (lastPressTimestamp - initialPressTimestamp) / NANOS_PER_MILLI;
    }
}
