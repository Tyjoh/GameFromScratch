package com.bytesmyth.lifegame.ui;

import com.bytesmyth.graphics.ui.*;
import com.bytesmyth.graphics.ui.positioning.HorizontalAlignment;
import com.bytesmyth.graphics.ui.positioning.RelativePositioning;
import com.bytesmyth.graphics.ui.positioning.VerticalAlignment;

public class TestUI extends Gui {

    public TestUI() {
        //Default / Exact positioning test
        Pane exactPane = new Pane();
        exactPane.setPosition(50, -20);
        exactPane.setSize(360, 240);
        this.addNode(exactPane);

        Pane topLeftExact = new Pane();
        topLeftExact.setSize(100, 20).setPosition(10, -10);
        Label topLeftExactLabel = new Label("Top left. Exact").setFontSize(8f);
        topLeftExactLabel.addMousePressListener((mouse) -> {
            System.out.println("Exact Top Left label pressed! lmb: " + mouse.getLeftButton().isPressed()
                    + ", rmb: " + mouse.getRightButton().isPressed());

            return false;
        });
        topLeftExact.addNode(topLeftExactLabel);
        exactPane.addNode(topLeftExact);

        Pane bottomRightExact = new Pane();
        bottomRightExact.setSize(100, 20).setPosition(250, -210);
        bottomRightExact.addNode(new Label("Bottom right. Exact").setFontSize(8f));
        exactPane.addNode(bottomRightExact);

        // Relative Positioning Test
        Pane relativePositioningPane = new Pane();
        relativePositioningPane.setSize(360, 240);
        relativePositioningPane.setPositioning(RelativePositioning.bottomRight(20));
        relativePositioningPane.addMousePressListener((mouse -> {
            System.out.println("Relative positioning pane pressed! Mouse pos: " + mouse.getPosition());
            return false;
        }));
        this.addNode(relativePositioningPane);

        Pane topRightRelative = new Pane();
        topRightRelative.setSize(140, 20).setPositioning(RelativePositioning.topRight(10));
        Label topRightRelativeLabel = new Label("Top Right. Relative");
        topRightRelativeLabel.setFontSize(8f).setPositioning(RelativePositioning.center());
        topRightRelative.addNode(topRightRelativeLabel);
        relativePositioningPane.addNode(topRightRelative);

        Pane centerLeftRelative = new Pane();
        RelativePositioning centerLeftPositioning = new RelativePositioning(HorizontalAlignment.LEFT, VerticalAlignment.CENTER, 10, 0);
        centerLeftRelative.setSize(140, 20).setPositioning(centerLeftPositioning);
        Label centerLeftRelativeLabel = new Label("Center Left. Relative");
        centerLeftRelativeLabel.addMousePressListener((mouse) -> {

            System.out.println("Center Left label pressed! lmb: " + mouse.getLeftButton().isPressed()
                    + ", rmb: " + mouse.getRightButton().isPressed());

            return false;
        });
        centerLeftRelativeLabel.setFontSize(8f).setPositioning(RelativePositioning.center());
        centerLeftRelative.addNode(centerLeftRelativeLabel);
        relativePositioningPane.addNode(centerLeftRelative);

        Button leftButton = new Button("Left");
        leftButton.setSize(60, 20);
        leftButton.setPositioning(new RelativePositioning(HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM, -35, 8));
        leftButton.addMousePressListener((mouse) -> {
            System.out.println("Left button pressed!");
            return false;
        });
        relativePositioningPane.addNode(leftButton);

        Button rightButton = new Button("Right");
        rightButton.setSize(60, 20);
        rightButton.setPositioning(new RelativePositioning(HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM, 35, 8));
        rightButton.addMousePressListener((mouse) -> {
            System.out.println("Right button pressed!");
            return false;
        });
        relativePositioningPane.addNode(rightButton);
    }

}
