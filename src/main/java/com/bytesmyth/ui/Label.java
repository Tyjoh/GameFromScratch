package com.bytesmyth.ui;

public class Label extends Node {

    private String text;

    public Label(String text) {
        super(0, 0);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Label setText(String text) {
        this.text = text;
        return this;
    }
}
