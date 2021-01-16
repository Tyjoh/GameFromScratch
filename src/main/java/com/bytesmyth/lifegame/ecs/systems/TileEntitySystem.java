package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.*;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import com.bytesmyth.lifegame.ecs.components.TileEntity;
import com.bytesmyth.lifegame.tilemap.TileMap;

public class TileEntitySystem extends IteratingSystem {

    private ComponentMapper<TileEntity> mTileEntities;

    @Wire
    private TileMap map;

    public TileEntitySystem() {
        super(Aspect.one(TileEntity.class));
    }

    @Override
    protected void initialize() {
        this.subscription.addSubscriptionListener(new TileEntityLinker(world, map, mTileEntities));
    }

    @Override
    protected void process(int entityId) {
        TileEntity tileEntity = mTileEntities.get(entityId);
        tileEntity.getBehavior().update(map, world.getEntity(entityId));
    }

    private static class TileEntityLinker implements EntitySubscription.SubscriptionListener {
        private final World world;
        private final TileMap tileMap;
        private ComponentMapper<TileEntity> mTileEntities;

        private TileEntityLinker(World world, TileMap tileMap, ComponentMapper<TileEntity> mTileEntities) {
            this.world = world;
            this.tileMap = tileMap;
            this.mTileEntities = mTileEntities;
        }

        @Override
        public void inserted(IntBag entities) {
            for (int id = 0; id < entities.size(); id++) {
                if (!mTileEntities.has(id)) continue;

                TileEntity tileEntity = mTileEntities.get(id);
                tileMap.getTile(tileEntity.getLayer(), tileEntity.getX(), tileEntity.getY()).setDynamicEntityId(id);
            }
        }

        @Override
        public void removed(IntBag entities) {
            for (int id = 0; id < entities.size(); id++) {
                Entity entity = world.getEntity(id);
                TileEntity tileEntity = entity.getComponent(TileEntity.class);
                tileMap.getLayer(tileEntity.getLayer()).setTile(tileEntity.getX(), tileEntity.getY(), null);
            }
        }
    }
}
