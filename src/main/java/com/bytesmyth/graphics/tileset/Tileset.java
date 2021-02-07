package com.bytesmyth.graphics.tileset;

import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureAtlas;

import java.util.*;
import java.util.stream.Collectors;

public class Tileset {

    private TextureAtlas atlas;
    private TileDef[] tiles;

    private Map<String, TileDef> typeMap = new HashMap<>();
    private Map<String, Tiler> autoTilers = new HashMap<>();

    public Tileset(TextureAtlas atlas) {
        this.atlas = atlas;
        this.tiles = new TileDef[atlas.getTileCount()];

        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new TileDefBuilder()
                    .tileId(i)
                    .build(atlas);
        }
    }

    public void addAll(List<TileDef> customDefs) {
        //add all default variants first
        for (TileDef def : customDefs) {
            if (def.isDefaultVariant()) {
                add(def);
            }
        }

        for (TileDef def : customDefs) {
            if (!def.isDefaultVariant()) {
                add(def);
            }
        }
    }

    public void add(TileDef def) {
        if (def.isDefaultVariant()) {

            if (typeMap.containsKey(def.getType())) {
                TileDef original = typeMap.get(def.getType());
                for (TileDef variant : original.getVariants()) {
                    def.addVariant(variant);
                }
            }

            typeMap.put(def.getType(), def);
            tiles[def.getId()] = def;
        } else if(typeMap.containsKey(def.getType())) {
            typeMap.get(def.getType()).addVariant(def);
            tiles[def.getId()] = def;
        } else {
            throw new IllegalStateException("No default type for " + def.getType() + " was added");
        }
    }

    public void addTiler(String tileType, Tiler tiler) {
        this.autoTilers.put(tileType, tiler);
    }

    public Tiler getTiler(String type) {
        return autoTilers.get(type);
    }

    public Tiler getCombinedTiler() {
        return new CombinedTiler();
    }

    public TileDef getTile(int x, int y) {
        return tiles[atlas.tileCoordToId(x, y)];
    }

    public TileDef getTile(int id) {
        return tiles[id];
    }

    public TileDef getTile(String type) {
        return typeMap.get(type);
    }

    public TileDef getTile(String type, String variant) {
        if (!typeMap.containsKey(type)) return null;
        return typeMap.get(type).getVariant(variant);
    }

    public List<TileDef> getCustomDefs() {
        return Arrays.stream(tiles)
                .filter(def -> def.getType() != null)
                .sorted(Comparator.comparing(TileDef::getType))
                .collect(Collectors.toList());
    }

    public Texture getTexture() {
        return atlas.getTexture();
    }

    public int getTileSize() {
        return atlas.getTileWidth();
    }

    public int getTileCount() {
        return atlas.getTileCount();
    }

    private class CombinedTiler implements Tiler {
        @Override
        public TileDef tile(int x, int y, TileTypeProvider tileTypeProvider) {
            String type = tileTypeProvider.get(x, y);
            if (type == null) return null;

            if (autoTilers.containsKey(type)) return autoTilers.get(type).tile(x, y, tileTypeProvider);
            return null;
        }
    }
}
