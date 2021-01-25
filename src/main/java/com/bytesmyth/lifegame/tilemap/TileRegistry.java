package com.bytesmyth.lifegame.tilemap;

import com.bytesmyth.graphics.texture.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class TileRegistry {

    private Map<String, Variants> textureRegions = new HashMap<>();
    private Map<String, Tiler> tilers = new HashMap<>();

    public void register(String tileType, TextureRegion region) {
        if (textureRegions.containsKey(tileType)) {
            throw new IllegalStateException("Default '" + tileType + "' has already been registered. Use unique tileType or register with variant.");
        }
        textureRegions.put(tileType, new Variants(region));
    }

    public void register(String tileType, String variant, TextureRegion region) {
        if (!textureRegions.containsKey(tileType)) {
            throw new IllegalStateException("Must register default '" + tileType + "' tile before registering variant '" + variant + "'");
        }
        textureRegions.get(tileType).put(variant, region);
    }

    public void registerTiler(String tileType, Tiler tiler) {
        this.tilers.put(tileType, tiler);
    }

    public TextureRegion getRegion(String tileType, String variant) {
        Variants variants = textureRegions.get(tileType);
        if (variants == null) {
            throw new IllegalArgumentException("No tile type '" + tileType + "' registered");
        }

        if (variant == null) {
            return variants.defaultVariant;
        } else if (variants.containsKey(variant)) {
            return variants.get(variant);
        } else {
            throw new IllegalArgumentException("No variant '" + variant + "' registered for tile '" + tileType + "'");
        }
    }

    public TextureRegion getRegion(String tileType) {
        return getRegion(tileType, null);
    }

    public Tile create(String tileType, String variant) {
        return new Tile(tileType).setVariant(variant).setTextureRegion(getRegion(tileType, variant));
    }

    public Tile create(String tileType) {
        return create(tileType, null);
    }

    public Tiler getTiler(String tileType) {
        return tilers.get(tileType);
    }

    public Tiler getCombinedTiler() {
        return (x, y, layer) -> {
            Tile tile = layer.getTile(x, y);
            if (tile == null) return;
            Tiler tiler = tilers.get(tile.getType());
            if (tiler != null) tiler.tile(x, y, layer);
        };
    }

    private static class Variants extends HashMap<String, TextureRegion> {
        public final TextureRegion defaultVariant;

        public Variants(TextureRegion defaultVariant) {
            this.defaultVariant = defaultVariant;
        }
    }
}
