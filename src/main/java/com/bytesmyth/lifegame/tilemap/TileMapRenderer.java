package com.bytesmyth.lifegame.tilemap;

import com.bytesmyth.graphics.StaticTextureQuadGraphicsElement;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.Graphics;
import com.bytesmyth.util.Pool;
import org.joml.Vector4f;

import java.util.Arrays;
import java.util.List;

public class TileMapRenderer {

    private final Graphics graphics;
    private Texture texture;

    private List<String> renderLayers = Arrays.asList("0", "1", "2");

    public TileMapRenderer(Graphics graphics, Texture texture) {
        this.graphics = graphics;
        this.texture = texture;
    }

    public void render(TileMap map) {
        Vector4f viewBounds = graphics.getViewBounds();

        int minX = Math.max((int) viewBounds.x - 1, 0);
        int maxX = Math.min((int) viewBounds.y + 1, map.getWidth()-1);
        int minY = Math.max((int) viewBounds.z - 1, 0);
        int maxY = Math.min((int) viewBounds.w + 1, map.getHeight()-1);

        int layer = 0;
        Pool<StaticTextureQuadGraphicsElement> graphicsPool = graphics.getElementPool(StaticTextureQuadGraphicsElement.class);

        for (String renderLayer : renderLayers) {
            TileMapLayer mapLayer = map.getLayer(renderLayer);
            for (int y = minY; y <= maxY; y++) {
                for (int x = minX; x <= maxX; x++) {
                    Tile tile = mapLayer.getTile(x, y);
                    if (tile == null || tile.getTextureRegion() == null) {
                        continue;
                    }

                    StaticTextureQuadGraphicsElement element = graphicsPool.get();
                    element.set(texture, tile.getTextureRegion(), x, y, 1, 1, layer);
                    graphics.enqueue(element);
                }
            }

            layer++;
        }
    }
}
