package com.bytesmyth.lifegame.domain.tile;

import com.artemis.Entity;
import com.bytesmyth.lifegame.domain.item.Item;
import com.bytesmyth.lifegame.tilemap.TileMap;

public class FarmTileLogic implements TileLogic {

    private int mature;
    private int age = 0;
    private String itemType;

    public FarmTileLogic(String itemType) {
        this.itemType = itemType;
        genMatureTime();
    }

    public boolean isMature() {
        return age >= mature;
    }

    public int getMatureAge() {
        return mature;
    }

    public int getAge() {
        return age;
    }

    @Override
    public void tick(TileMap map, Entity entity) {
        age++;
    }

    public Item harvest() {
        age = 0;
        genMatureTime();
        return new Item(itemType);
    }

    private void genMatureTime() {
        this.mature = (int) (750 + Math.random() * 2000);
    }
}
