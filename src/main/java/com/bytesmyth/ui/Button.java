package com.bytesmyth.ui;

import org.joml.Vector3f;

import java.util.LinkedList;
import java.util.List;

public class Button extends Node {

    public interface Listener {
        void onClicked();
    }

    private String text;

    private Vector3f color = new Vector3f(1,1,1);
    private Vector3f textColor = new Vector3f(0,0,0);

    private List<Listener> listeners = new LinkedList<>();

    public Button(String text, float w, float h) {
        super(w, h);
        this.text = text;
    }

    public Button addListener(Listener listener) {
        this.listeners.add(listener);
        return this;
    }

    public String getText() {
        return text;
    }

    public Vector3f getColor() {
        return color;
    }

    public Button setColor(Vector3f color) {
        this.color = color;
        return this;
    }

    public Vector3f getTextColor() {
        return textColor;
    }

    public Button setTextColor(Vector3f textColor) {
        this.textColor = textColor;
        return this;
    }

    void fireClick() {
        for (Listener listener : listeners) {
            listener.onClicked();
        }
    }
}
