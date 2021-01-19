package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.*;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import com.bytesmyth.lifegame.ecs.components.TileComponent;
import com.bytesmyth.lifegame.tilemap.TileMap;

public class TileEntitySystem extends IteratingSystem {

    private ComponentMapper<TileComponent> mTileEntities;

    @Wire
    private TileMap map;

    public TileEntitySystem() {
        super(Aspect.one(TileComponent.class));
    }

    @Override
    protected void initialize() {
        this.subscription.addSubscriptionListener(new TileEntityLinker(world, map, mTileEntities));
    }

    @Override
    protected void process(int entityId) {
        TileComponent tileComponent = mTileEntities.get(entityId);
        tileComponent.getBehavior().update(map, world.getEntity(entityId));
    }

    private static class TileEntityLinker implements EntitySubscription.SubscriptionListener {
        private final World world;
        private final TileMap tileMap;
        private ComponentMapper<TileComponent> mTileEntities;

        private TileEntityLinker(World world, TileMap tileMap, ComponentMapper<TileComponent> mTileEntities) {
            this.world = world;
            this.tileMap = tileMap;
            this.mTileEntities = mTileEntities;
        }

        @Override
        public void inserted(IntBag entities) {
            for (int id = 0; id < entities.size(); id++) {
                if (!mTileEntities.has(id)) continue;

                TileComponent tileComponent = mTileEntities.get(id);
                tileMap.getTile(tileComponent.getLayer(), tileComponent.getX(), tileComponent.getY()).setDynamicEntityId(id);
            }
        }

        @Override
        public void removed(IntBag entities) {
            for (int id = 0; id < entities.size(); id++) {
                Entity entity = world.getEntity(id);
                TileComponent tileComponent = entity.getComponent(TileComponent.class);
                tileMap.getLayer(tileComponent.getLayer()).setTile(tileComponent.getX(), tileComponent.getY(), null);
            }
        }
    }
}
