package com.bytesmyth.graphics.tileset;

import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.graphics.texture.TextureRegion;

public class TileDefBuilder {
    private Integer id;

    private Integer x;
    private Integer y;

    private String type;
    private String variant;

    private Boolean defaultVariant;

    public TileDefBuilder() { }

    public TileDefBuilder(TileDef def) {
        this.id = def.getId();
        this.x = def.getTileX();
        this.y = def.getTileY();
        this.type = def.getType();
        this.variant = def.getVariant();
        if (def.isDefaultVariant()) {
            this.defaultVariant = true;
        }
    }

    public TileDefBuilder tile(int x, int y) {
        if (this.id != null) throw new IllegalStateException("Tile id already specified");
        this.x = x;
        this.y = y;
        return this;
    }

    public TileDefBuilder tileId(int id) {
        if (this.x != null) throw new IllegalStateException("Tile coordinates already specified");
        this.id = id;
        return this;
    }

    public TileDefBuilder type(String type) {
        this.type = type;
        return this;
    }

    public TileDefBuilder variant(String variant) {
        this.variant = variant;
        return this;
    }

    public TileDefBuilder defaultVariant() {
        this.defaultVariant = true;
        return this;
    }

    public TileDef build(TextureAtlas atlas) {
        TextureRegion region;
        if (id != null) {
            region = atlas.getRegionById(id);
            this.x = atlas.idToX(id);
            this.y = atlas.idToY(id);
        } else if(x != null) {
            region = atlas.getRegionByCoord(x, y);
            id = atlas.tileCoordToId(x, y);
        } else {
            throw new IllegalStateException("Cannot build TileDef. id or tile coordinates required.");
        }

        return new TileDef(region, id, x, y, type, variant, defaultVariant != null && defaultVariant);
    }
}
