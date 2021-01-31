package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.graphics.texture.TextureRegion;
import com.bytesmyth.lifegame.ecs.components.TileComponent;
import com.bytesmyth.lifegame.ecs.components.TileGraphicsComponent;
import com.bytesmyth.lifegame.tilemap.Tile;
import com.bytesmyth.lifegame.tilemap.TileMap;

@All({TileComponent.class, TileGraphicsComponent.class})
public class TileGraphicsSystem extends IteratingSystem {

    private ComponentMapper<TileComponent> mTile;
    private ComponentMapper<TileGraphicsComponent> mTileGraphics;

    @Wire
    private TileMap map;

    @Override
    protected void process(int entityId) {
        TileComponent tileComp = mTile.get(entityId);
        TileGraphicsComponent tileGraphics = mTileGraphics.get(entityId);

        TextureRegion region = tileGraphics.getTextureRegion();
        Tile tile = map.getLayer(tileComp.getLayer()).getTile(tileComp.getX(), tileComp.getY());
        if (tile != null) {
            tile.setTextureRegion(region);
        }
    }
}
