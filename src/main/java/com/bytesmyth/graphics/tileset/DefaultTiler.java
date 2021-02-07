package com.bytesmyth.graphics.tileset;

public class DefaultTiler implements Tiler {
    private TileDef tileDef;

    public DefaultTiler(TileDef tileDef) {
        this.tileDef = tileDef;
    }

    @Override
    public TileDef tile(int x, int y, TileTypeProvider tileTypeProvider) {
        String type = tileTypeProvider.get(x, y);
        if (!tileDef.getType().equals(type)) {
            return null;
        } else {
            return tileDef;
        }
    }
}
