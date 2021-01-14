package com.bytesmyth.lifegame.tilemap;

import com.bytesmyth.graphics.batch.QuadTextureBatcher;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.graphics.texture.TextureRegion;
import org.joml.Vector4f;

import java.util.Arrays;
import java.util.List;

public class TileMapRenderer {

    private OrthographicCamera2D camera;
    private QuadTextureBatcher quadBatcher;

    private List<String> renderLayers = Arrays.asList("ground", "1", "2");

    private final TileRegistry tileRegistry;

    public TileMapRenderer(OrthographicCamera2D camera, QuadTextureBatcher quadBatcher, TileRegistry tileRegistry) {
        this.camera = camera;
        this.quadBatcher = quadBatcher;
        this.tileRegistry = tileRegistry;
    }

    public void render(TileMap map, TextureAtlas atlas) {
        quadBatcher.begin(atlas.getTexture());

        Vector4f viewBounds = camera.getViewBounds();

        int minX = Math.max((int) viewBounds.x - 1, 0);
        int maxX = Math.min((int) viewBounds.y + 1, map.getWidth()-1);
        int minY = Math.max((int) viewBounds.z - 1, 0);
        int maxY = Math.min((int) viewBounds.w + 1, map.getHeight()-1);

        for (String renderLayer : renderLayers) {
            for (int y = minY; y <= maxY; y++) {
                for (int x = minX; x <= maxX; x++) {
                    Tile tile = map.getTile(renderLayer, x, y);
                    if (tile == null) {
                        continue;
                    }
                    TextureRegion region = tileRegistry.getRenderer(tile.getId()).render(x, y, tile);
                    quadBatcher.draw(x - 0.5f, y + 0.5f, x + 0.5f, y - 0.5f, region);
                }
            }
        }

        quadBatcher.end();
    }
}
