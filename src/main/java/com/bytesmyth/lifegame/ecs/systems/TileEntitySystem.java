package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.*;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import com.bytesmyth.lifegame.ecs.components.TileComponent;
import com.bytesmyth.lifegame.tilemap.Tile;
import com.bytesmyth.lifegame.tilemap.TileMap;

public class TileEntitySystem extends IteratingSystem {

    private ComponentMapper<TileComponent> mTileEntities;

    @Wire
    private TileMap map;

    public TileEntitySystem() {
        super(Aspect.one(TileComponent.class));
    }

    @Override
    protected void process(int entityId) {
        TileComponent tileComponent = mTileEntities.get(entityId);
        tileComponent.getBehavior().tick(map, world.getEntity(entityId));
    }

}
