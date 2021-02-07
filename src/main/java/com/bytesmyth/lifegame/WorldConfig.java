package com.bytesmyth.lifegame;

import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.bytesmyth.lifegame.ecs.systems.*;

public class WorldConfig {
    public static WorldConfiguration createDefault() {
        return new WorldConfigurationBuilder()
                //moving entities in the world
                .with(new PrevTransformSystem())
                .with(new UserControlSystem())
                .with(new PositionIntegrationSystem())
                .with(new TileMapCollisionSystem())
                .with(new SpatialPartitioningSystem())

                //game logic stuffs
                .with(new TileEntitySystem())
                .with(new InteractSystem())
                .with(new ItemPickupSystem())

                //graphics
                .with(new CameraFollowSystem())
                .with(new AnimationSystem())
                .with(new TileGraphicsSystem())
                .with(new SpriteRenderingSystem())
                .build();
    }
}
