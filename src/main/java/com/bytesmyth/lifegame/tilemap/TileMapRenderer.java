package com.bytesmyth.lifegame.tilemap;

import com.bytesmyth.graphics.batch.QuadTextureBatcher;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.graphics.mesh.Rectangle;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.graphics.texture.TextureRegion;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.Arrays;
import java.util.List;

public class TileMapRenderer {

    private OrthographicCamera2D camera;
    private QuadTextureBatcher quadBatcher;

    private List<String> renderLayers = Arrays.asList("ground", "object1", "object2");

    public TileMapRenderer(OrthographicCamera2D camera, QuadTextureBatcher quadBatcher) {
        this.camera = camera;
        this.quadBatcher = quadBatcher;
    }

    public void render(TileMap map, TextureAtlas atlas) {
        quadBatcher.begin(atlas.getTexture());

        Vector4f viewBounds = camera.getViewBounds();

        int minX = (int) viewBounds.x - 1;
        int maxX = (int) viewBounds.y + 1;
        int minY = (int) viewBounds.z - 1;
        int maxY = (int) viewBounds.w + 1;

        for (String renderLayer : renderLayers) {
            for (int y = minY; y <= maxY; y++) {
                for (int x = minX; x <= maxX; x++) {
                    int id = map.get(renderLayer, x, y);
                    if (id > -1) {
                        TextureRegion textureRegion = atlas.getRegionById(id);
                        quadBatcher.draw(x - 0.5f, y + 0.5f, x + 0.5f, y - 0.5f, textureRegion);
                    }
                }
            }
        }

        quadBatcher.end();
    }
}
