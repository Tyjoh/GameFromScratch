package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Entity;
import com.bytesmyth.lifegame.tilemap.TileMap;

public class FarmTileBehavior implements TileBehavior {

    private int mature;
    private int age = 0;

    public FarmTileBehavior() {
        genMatureTime();
    }

    public boolean isMature() {
        return age >= mature;
    }

    @Override
    public void update(TileMap map, Entity entity) {
        age++;
    }

    public void harvest() {
        age = 0;
        genMatureTime();
    }

    private void genMatureTime() {
        this.mature = (int) (750 + Math.random() * 2000);
    }
}
