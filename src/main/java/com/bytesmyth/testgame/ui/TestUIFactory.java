package com.bytesmyth.testgame.ui;

import com.bytesmyth.ui.*;
import org.joml.Vector3f;

public class TestUIFactory {

    public Container populateGui(Gui root) {
        Pane pane = new Pane(512, 256 + 128);
        root.addChild(pane, RelativePositioning.center());

        Button leftButton = new Button("Left", 128, 32)
                .setColor(new Vector3f(1f, 0.3f, 0.3f))
                .addListener(() -> System.out.println("Left pressed"));
        RelativePositioning left = RelativePositioning.bottomLeft(8);
        pane.addChild(leftButton, left);

        Button centerButton = new Button("CENTER", 128, 32)
                .setColor(new Vector3f(0.3f, 0.3f, 1f))
                .addListener(() -> System.out.println("Center pressed"));
        pane.addChild(centerButton, RelativePositioning.bottomCenter(8));

        Button rightButton = new Button("Right", 128, 32)
                .setColor(new Vector3f(0.3f, 1f, 0.3f))
                .addListener(() -> System.out.println("Right pressed"));
        pane.addChild(rightButton, RelativePositioning.bottomRight(8));

        Button sideButtonTop = new Button("",24, 24).addListener(() -> System.out.println("Button 1"));
        pane.addChild(sideButtonTop, new RelativePositioning(HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, -4, 0f));

        Button sideButton = new Button("",24, 24).addListener(() -> System.out.println("Button 2"));
        pane.addChild(sideButton, new RelativePositioning(HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, -4, 36f));

        Button sideButtonBottom = new Button("",24, 24).addListener(() -> System.out.println("Button 3"));
        pane.addChild(sideButtonBottom, new RelativePositioning(HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, -4, -36f));

        Label label = new Label("FPS: 57");
        label.setKey("fps_label");
        root.addChild(label, RelativePositioning.topLeft(4));

        return pane;
    }

}
