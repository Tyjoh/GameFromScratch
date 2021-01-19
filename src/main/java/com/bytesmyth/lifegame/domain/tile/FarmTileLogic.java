package com.bytesmyth.lifegame.domain.tile;

import com.artemis.Entity;
import com.bytesmyth.lifegame.tilemap.TileMap;

public class FarmTileLogic implements TileLogic {

    private int mature;
    private int age = 0;

    public FarmTileLogic() {
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
