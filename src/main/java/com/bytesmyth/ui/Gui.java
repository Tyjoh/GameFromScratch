package com.bytesmyth.ui;

import com.bytesmyth.graphics.batch.QuadTextureBatcher;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.graphics.font.BitmapFont;
import com.bytesmyth.graphics.texture.NinePatch;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.input.Input;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Gui extends Container {

    private QuadTextureBatcher batcher;
    private boolean enabled = true;

    private final Texture guiTexture;
    private OrthographicCamera2D camera;

    private final NinePatch windowPatch;
    private final NinePatch buttonPatch;

    private final BitmapFont font;

    private Node pressedNode = null;
    private Node hoveredNode = null;

    private Map<String, Node> keyNodeMap = new HashMap<>();

    public Gui(Texture guiTexture, QuadTextureBatcher batcher, OrthographicCamera2D camera) {
        super(0, 0);
        this.guiTexture = guiTexture;
        this.batcher = batcher;
        this.camera = camera;

        TextureAtlas atlas = new TextureAtlas(guiTexture, 16, 16);
        windowPatch = new NinePatch(atlas, 0,16);
        buttonPatch = new NinePatch(atlas, 3,16);
        font = new BitmapFont(guiTexture, "mono");
    }

    public void handleGuiInput(Input input) {
        if (!enabled) {
            return;
        }

        List<PositionedNode> nodeOrder = getNodeOrder();

        Vector2f mousePosition = camera.toCameraCoordinates(input.getMousePosition());

        hoveredNode = null;

        for (PositionedNode positionedNode : nodeOrder) {
            if (positionedNode.contains(mousePosition)) {
                if (hoveredNode != null) {
                    hoveredNode.setHovered(false);
                }
                hoveredNode = positionedNode.node;
                hoveredNode.setHovered(true);
            } else {
                positionedNode.node.setHovered(false);
            }
        }

        if (input.isMouseDown("left") && pressedNode == null && hoveredNode != null) { //if nothing is currently being pressed
            pressedNode = hoveredNode;
            pressedNode.setPressed(true);
        } else if(!input.isMouseDown("left") && pressedNode != null) {
            if (pressedNode instanceof Button) {
                ((Button) pressedNode).fireClick();
            }
            pressedNode.setPressed(false);
            pressedNode = null;
        }

    }

    public void render() {
        if (!enabled) {
            return;
        }

        List<PositionedNode> nodeOrder = getNodeOrder();

        batcher.begin(guiTexture);
        for (PositionedNode positionedNode : nodeOrder) {
            drawNode(positionedNode);
        }
        batcher.end();
    }

    private List<PositionedNode> getNodeOrder() {
        List<PositionedNode> positionedNodes = new LinkedList<>();

        Position rootPosition = new Position(0,0, camera.getWidth(), camera.getHeight());
        position(rootPosition, this, positionedNodes);
        return positionedNodes;
    }

    private void position(Position position, Node node, List<PositionedNode> output) {
        float hw = node.getWidth() / 2f;
        float hh = node.getHeight() / 2f;
        output.add(new PositionedNode(position.getX() - hw, position.getY() + hh, node.getWidth(), node.getHeight(), node));

        if (node instanceof Container) {
            positionChildren(position, (Container) node, output);
        }
    }

    private void positionChildren(Position parent, Container container, List<PositionedNode> output) {
        for (Node child : container.getChildren()) {
            Position position = container.getNodePositioning(child).position(parent, child, this);
            position(position, child, output);
        }
    }

    private void drawNode(PositionedNode node) {
        if (node.node instanceof Pane) {
            drawPane(node.position, (Pane) node.node);
        } else if (node.node instanceof Label) {
            drawLabel(node.position, (Label) node.node);
        } else if (node.node instanceof Button) {
            drawButton(node.position, (Button) node.node);
        }
    }

    private void drawLabel(Position p, Label label) {
        Vector2f size = font.getTextSize(label.getText(), 16f);
        float labelFontSize = 16;
        font.drawText(label.getText(), p.getX() + p.getWidth()/2f - size.x/2f, p.getY() - p.getHeight()/2f + size.y / 2f, labelFontSize, batcher);
    }

    private void drawPane(Position p, Pane container) {
        batcher.setColor(1,1,1, container.getOpacity());
        windowPatch.draw(p.getX(), p.getY(), p.getWidth(), p.getHeight(), batcher);
        batcher.setColor(1,1,1,1);
    }

    private void drawButton(Position p, Button button) {
        Vector3f color = new Vector3f(button.getColor());

        if (button.isPressed()) {
            color.mul(0.65f);
        } else if (button.isHovered()) {
            color.mul(0.85f);
        }

        batcher.setColor(color.x, color.y, color.z, 1f);
        buttonPatch.draw(p.getX(), p.getY(), p.getWidth(), p.getHeight(), batcher);

        Vector3f textColor = button.getTextColor();
        batcher.setColor(textColor.x, textColor.y, textColor.z, 1f);

        int buttonFontSize = 16;
        Vector2f size = font.getTextSize(button.getText(), buttonFontSize);
        font.drawText(button.getText(), p.getX() + p.getWidth()/2f - size.x/2f, p.getY() - p.getHeight()/2f + size.y / 2f, buttonFontSize, batcher);

        batcher.setColor(1,1,1,1);
    }

    public OrthographicCamera2D getCamera() {
        return camera;
    }

    public BitmapFont getFont() {
        return font;
    }

    @Override
    Gui getGui() {
        return this;
    }

    public void registerNode(Node node) {
        if (node.getKey() != null) {
            this.keyNodeMap.put(node.getKey(), node);
        }
    }

    public Node getNode(String key) {
        return keyNodeMap.get(key);
    }

    public boolean hasNode(String key) {
        return keyNodeMap.containsKey(key);
    }

    private static class PositionedNode {
        private Position position;
        private Node node;

        private PositionedNode(float x, float y, float w, float h, Node node) {
            position = new Position(x, y, w, h);
            this.node = node;
        }

        private PositionedNode(Position position, Node node) {
            this.position = position;
            this.node = node;
        }

        public boolean contains(Vector2f mousePosition) {
            if (mousePosition.x < position.getX() || mousePosition.x > position.getX() + position.getWidth()) {
                return false;
            }

            if (mousePosition.y < position.getY() - position.getHeight() || mousePosition.y > position.getY()) {
                return false;
            }

            return true;
        }
    }

}
