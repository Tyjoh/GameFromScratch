package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Entity;
import com.bytesmyth.lifegame.tilemap.TileMap;

public class FarmTileBehavior implements TileBehavior {

    private final int mature;
    private int age = 0;

    public FarmTileBehavior(int matureTicks) {
        this.mature = matureTicks;
    }

    public boolean isMature() {
        return age >= mature;
    }

    @Override
    public void update(TileMap map, Entity entity) {
        age++;
    }
}
