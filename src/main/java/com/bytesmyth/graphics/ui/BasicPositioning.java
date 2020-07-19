package com.bytesmyth.graphics.ui;

public class BasicPositioning implements RelativePositioning {

    private HorizontalAlignment alignmentH;
    private VerticalAlignment alignmentV;

    private float horizontalOffset;
    private float verticalOffset;

    public static BasicPositioning bottomLeft(float padding) {
        return new BasicPositioning(HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM, padding, padding);
    }

    public static BasicPositioning bottomRight(float padding) {
        return new BasicPositioning(HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM, padding, padding);

    }

    public static BasicPositioning bottomCenter(float padding) {
        return new BasicPositioning(HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM, 0, padding);
    }

    public BasicPositioning(HorizontalAlignment alignmentH, VerticalAlignment alignmentV, float horizontalOffset, float verticalOffset) {
        this.alignmentH = alignmentH;
        this.alignmentV = alignmentV;
        this.horizontalOffset = horizontalOffset;
        this.verticalOffset = verticalOffset;
    }

    @Override
    public HorizontalAlignment getHorizontalAlignment() {
        return alignmentH;
    }

    @Override
    public float getHorizontalOffset() {
        return horizontalOffset;
    }

    @Override
    public VerticalAlignment getVerticalAlignment() {
        return alignmentV;
    }

    @Override
    public float getVerticalOffset() {
        return verticalOffset;
    }
}
