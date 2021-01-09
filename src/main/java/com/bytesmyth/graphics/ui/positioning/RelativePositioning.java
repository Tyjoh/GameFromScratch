package com.bytesmyth.graphics.ui.positioning;

import com.bytesmyth.graphics.ui.Node;
import org.joml.Vector2f;

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

    public static Positioning center(float xOffset, float yOffset) {
        return new RelativePositioning(HorizontalAlignment.CENTER, VerticalAlignment.CENTER, xOffset, yOffset);
    }

    public static Positioning topRight(float padding) {
        return new RelativePositioning(HorizontalAlignment.RIGHT, VerticalAlignment.TOP, padding, padding);
    }

    public static Positioning topLeft(float padding) {
        return new RelativePositioning(HorizontalAlignment.LEFT, VerticalAlignment.TOP, padding, padding);
    }

    public Vector2f position(Vector2f size, Node node) {
        float left = 0;
        float right = size.x;
        float centerX = size.x / 2f;
        float top = 0;
        float bottom = -size.y;
        float centerY = -size.y / 2f;

        float nodeHW = node.getWidth() / 2f;
        float nodeHH = node.getHeight() / 2f;

        float targetLeft = left;
        float targetTop = top;

        switch (alignmentH) {
            case LEFT:
                if (horizontalOffset >= 0) {
                    targetLeft = left + horizontalOffset;
                } else {
                    throw new IllegalStateException("Horizontal offset must be positive for LEFT alignment");
                }
                break;
            case RIGHT:
                if (horizontalOffset >= 0) {
                    targetLeft = right - node.getWidth() - horizontalOffset;
                } else {
                    throw new IllegalStateException("Horizontal offset must be positive for RIGHT alignment");
                }
                break;
            case CENTER:
                targetLeft = centerX - nodeHW + horizontalOffset;
                break;
        }

        switch (alignmentV) {
            case TOP:
                if (verticalOffset >= 0) {
                    targetTop = top - verticalOffset;
                } else {
                    throw new IllegalStateException("Vertical offset must be positive for TOP alignment");
                }
                break;
            case BOTTOM:
                if (horizontalOffset >= 0) {
                    targetTop = bottom + node.getHeight() + verticalOffset;
                } else {
                    throw new IllegalStateException("Vertical offset must be positive for BOTTOM alignment");
                }
                break;
            case CENTER:
                targetTop = centerY + nodeHH + verticalOffset;
                break;
        }

        return new Vector2f(targetLeft, targetTop);
    }
}
