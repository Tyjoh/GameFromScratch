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
        this.addChild(exactPane);

        Pane topLeftExact = new Pane();
        topLeftExact.setSize(100, 20).setPosition(10, -10);
        topLeftExact.addChild(new Label("Top left. Exact").setFontSize(8f));
        exactPane.addChild(topLeftExact);

        Pane bottomRightExact = new Pane();
        bottomRightExact.setSize(100, 20).setPosition(250, -210);
        bottomRightExact.addChild(new Label("Bottom right. Exact").setFontSize(8f));
        exactPane.addChild(bottomRightExact);

        // Relative Positioning Test
        Pane relativePositioningPane = new Pane();
        relativePositioningPane.setSize(360, 240);
        relativePositioningPane.setPositioning(RelativePositioning.bottomRight(20));
        this.addChild(relativePositioningPane);

        Pane topRightRelative = new Pane();
        topRightRelative.setSize(140, 20).setPositioning(RelativePositioning.topRight(10));
        Label topRightRelativeLabel = new Label("Top Right. Relative");
        topRightRelativeLabel.setFontSize(8f).setPositioning(RelativePositioning.center());
        topRightRelative.addChild(topRightRelativeLabel);
        relativePositioningPane.addChild(topRightRelative);

        Pane centerLeftRelative = new Pane();
        RelativePositioning centerLeftPositioning = new RelativePositioning(HorizontalAlignment.LEFT, VerticalAlignment.CENTER, 10, 0);
        centerLeftRelative.setSize(140, 20).setPositioning(centerLeftPositioning);
        Label centerLeftRelativeLabel = new Label("Center Left. Relative");
        centerLeftRelativeLabel.setFontSize(8f).setPositioning(RelativePositioning.center());
        centerLeftRelative.addChild(centerLeftRelativeLabel);
        relativePositioningPane.addChild(centerLeftRelative);
    }

}
