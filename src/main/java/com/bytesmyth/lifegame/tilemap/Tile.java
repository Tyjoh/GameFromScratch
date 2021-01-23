package com.bytesmyth.lifegame.tilemap;

import com.bytesmyth.graphics.sprite.Sprite;

public class Tile {

    private TileMapLayer layer;

    private final String id;
    private boolean dynamic;
    private int entityId;

    private Sprite sprite;

    public Tile(String id) {
        this.dynamic = false;
        this.id = id;
        this.entityId = -1;//invalid id.
    }

    public TileMapLayer getLayer() {
        return layer;
    }

    void setLayer(TileMapLayer layer) {
        this.layer = layer;
    }

    public String getId() {
        return id;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setDynamicEntityId(int id) {
        this.entityId = id;
        this.dynamic = true;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Tile setSprite(Sprite sprite) {
        this.sprite = sprite;
        return this;
    }
}
