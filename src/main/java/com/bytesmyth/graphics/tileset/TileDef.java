package com.bytesmyth.graphics.tileset;

import com.bytesmyth.graphics.texture.TextureRegion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileDef {

    private final TextureRegion region;

    private final int id;
    private final int tileX;
    private final int tileY;

    private final String type;
    private final String variant;
    private final boolean defaultVariant;

    private Map<String, TileDef> variants = new HashMap<>();

    public TileDef(TextureRegion region, int id, int tileX, int tileY, String type, String variant, boolean defaultVariant) {
        this.region = region;
        this.id = id;
        this.tileX = tileX;
        this.tileY = tileY;
        this.type = type;
        this.variant = variant;
        this.defaultVariant = defaultVariant;
        if (this.variant != null) {
            this.variants.put(variant, this);
        }
    }

    public TextureRegion getRegion() {
        return region;
    }

    public String getType() {
        return type;
    }

    public TileDef getVariant(String variant) {
        return variants.get(variant);
    }

    public String getVariant() {
        return variant;
    }

    void addVariant(TileDef def) {
        if (!this.type.equals(def.getType())) throw new RuntimeException("Cannot add variant of '" + def.type + "' to '" + type + "' tileDef");
        this.variants.put(def.getVariant(), def);
    }

    public boolean isDefaultVariant() {
        return defaultVariant;
    }

    public int getId() {
        return id;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public List<TileDef> getVariants() {
        return new ArrayList<>(variants.values());
    }
}
