package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Entity;
import com.bytesmyth.lifegame.tilemap.TileMap;

public interface TileBehavior {
    void update(TileMap map, Entity entity);
}
