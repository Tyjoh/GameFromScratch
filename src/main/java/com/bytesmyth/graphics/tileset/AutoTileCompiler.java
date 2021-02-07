package com.bytesmyth.graphics.tileset;

import java.util.Arrays;

public class AutoTileCompiler {

    public static AutoTiler compile(Tileset tileset, AutoTileDef def) {
        String type = def.type;
        String testType = def.testType;
        if (testType == null) testType = type;

        TileDef[] tiles = new TileDef[256];
        Arrays.fill(tiles, tileset.getTile(def.type));


        for (AutoTileRule rule : def.rules) {
            int forbidden = rule.getForbidden();
            int required = rule.getRequired();

            for (int bitmask = 0; bitmask < 256; bitmask++) {
                if ((bitmask & forbidden) != 0) continue;
                if ((bitmask & required) != required) continue;

                tiles[bitmask] = tileset.getTile(def.type, rule.getVariant());
            }
        }

        return new AutoTiler(type, testType, tiles);
    }

}
