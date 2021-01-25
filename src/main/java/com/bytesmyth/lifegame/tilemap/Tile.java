package com.bytesmyth.lifegame.tilemap;

import com.bytesmyth.graphics.texture.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class Tile {

    private TileMapLayer layer;

    private String type;
    private String variant;

    private boolean dynamic;
    private int entityId;

    private TextureRegion textureRegion;

    public Tile(String type, String variant) {
        this.type = type;
        this.variant = variant;

        this.dynamic = false;
        this.entityId = -1;//invalid id.
    }

    public Tile(String type) {
        this(type, null);
    }

    public TileMapLayer getLayer() {
        return layer;
    }

    Tile setLayer(TileMapLayer layer) {
        this.layer = layer;
        return this;
    }

    public String getType() {
        return type;
    }

    public String getVariant() {
        return variant;
    }

    public Tile setVariant(String variant) {
        this.variant = variant;
        return this;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public int getEntityId() {
        return entityId;
    }

    public Tile setDynamicEntityId(int id) {
        this.entityId = id;
        this.dynamic = true;
        return this;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public Tile setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        return this;
    }
}
