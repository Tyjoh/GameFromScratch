package com.bytesmyth.game;

public interface MouseHandler {
    void onCursorPosChanged(double x, double y);

    void onButtonPressed(int button);

    void onButtonReleased(int button);
}
