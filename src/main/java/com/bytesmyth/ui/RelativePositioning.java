package com.bytesmyth.ui;

public class RelativePositioning implements Positioning {

    private HorizontalAlignment alignmentH;
    private VerticalAlignment alignmentV;

    private float horizontalOffset;
    private float verticalOffset;


    public RelativePositioning(HorizontalAlignment alignmentH, VerticalAlignment alignmentV, float horizontalOffset, float verticalOffset) {
        this.alignmentH = alignmentH;
        this.alignmentV = alignmentV;
        this.horizontalOffset = horizontalOffset;
        this.verticalOffset = verticalOffset;
    }

    public static RelativePositioning bottomLeft(float padding) {
        return new RelativePositioning(HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM, padding, padding);
    }

    public static RelativePositioning bottomRight(float padding) {
        return new RelativePositioning(HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM, padding, padding);
    }

    public static RelativePositioning bottomCenter(float padding) {
        return new RelativePositioning(HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM, 0, padding);
    }

    public static Positioning center() {
        return new RelativePositioning(HorizontalAlignment.CENTER, VerticalAlignment.CENTER, 0, 0);
    }

    public static Positioning topRight(float padding) {
        return new RelativePositioning(HorizontalAlignment.RIGHT, VerticalAlignment.TOP, padding, padding);
    }

    public static Positioning topLeft(float padding) {
        return new RelativePositioning(HorizontalAlignment.LEFT, VerticalAlignment.TOP, padding, padding);
    }

    public Position position(Position parent, Node child) {
        float x = parent.getX();
        float y = parent.getY();

        float childHW = child.getWidth() / 2f;
        float childHH = child.getHeight() / 2f;

        float targetX = x;
        float targetY = y;

        switch (alignmentH) {
            case LEFT:
                if (horizontalOffset >= 0) {
                    targetX = x - parent.getHalfWidth() + childHW + horizontalOffset;
                } else {
                    targetX = x - parent.getHalfWidth() - childHW + horizontalOffset;
                }
                break;
            case RIGHT:
                if (horizontalOffset >= 0) {
                    targetX = x + parent.getHalfWidth() - childHW - horizontalOffset;
                } else {
                    targetX = x + parent.getHalfWidth() + childHW - horizontalOffset;
                }
                break;
            case CENTER:
                targetX = x + horizontalOffset;
                break;
        }

        switch (alignmentV) {
            case BOTTOM:
                if (verticalOffset >= 0) {
                    targetY = y - parent.getHalfHeight() + childHH + verticalOffset;
                } else {
                    targetY = y - parent.getHalfHeight() - childHH + verticalOffset;
                }
                break;
            case TOP:
                if (verticalOffset >= 0) {
                    targetY = y + parent.getHalfHeight() - childHH - verticalOffset;
                } else {
                    targetY = y + parent.getHalfHeight() + childHH - verticalOffset;
                }
                break;
            case CENTER:
                targetY = y + verticalOffset;
                break;
        }

        return new Position(targetX, targetY, child.getWidth(), child.getHeight());
    }
}
