package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.*;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import com.bytesmyth.lifegame.ecs.components.TileEntity;
import com.bytesmyth.lifegame.tilemap.TileMap;

public class TileEntitySystem extends IteratingSystem {

    private ComponentMapper<TileEntity> tileEntities;

    @Wire
    private TileMap map;

    public TileEntitySystem() {
        super(Aspect.one(TileEntity.class));
    }

    @Override
    protected void initialize() {
        this.subscription.addSubscriptionListener(new TileEntityLinker(world, map));
    }

    @Override
    protected void process(int entityId) {
        TileEntity tileEntity = tileEntities.get(entityId);
        tileEntity.getBehavior().update(map, world.getEntity(entityId));
    }

    private static class TileEntityLinker implements EntitySubscription.SubscriptionListener {
        private final World world;
        private final TileMap tileMap;

        private TileEntityLinker(World world, TileMap tileMap) {
            this.world = world;
            this.tileMap = tileMap;
        }

        @Override
        public void inserted(IntBag entities) {
            for (int id = 0; id < entities.size(); id++) {
                TileEntity tileEntity = world.getEntity(id).getComponent(TileEntity.class);
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
