package com.bytesmyth.lifegame.domain.tile;

import com.artemis.Entity;
import com.bytesmyth.lifegame.tilemap.TileMap;

public interface TileLogic {
    void update(TileMap map, Entity entity);
}
