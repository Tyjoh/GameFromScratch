package com.bytesmyth.lifegame;

import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.bytesmyth.lifegame.ecs.systems.*;

public class WorldConfig {
    public static WorldConfiguration createDefault() {
        return new WorldConfigurationBuilder()
                .with(new PrevTransformSystem())
                .with(new EntityControlSystem())
                .with(new PositionIntegrationSystem())
                .with(new TileEntitySystem())
                .with(new TileMapCollisionSystem())
                .with(new InteractSystem())
                .with(new ItemPickupSystem())
                .with(new CameraFollowSystem())
                .with(new SpriteRenderingSystem())
                .build();
    }
}
