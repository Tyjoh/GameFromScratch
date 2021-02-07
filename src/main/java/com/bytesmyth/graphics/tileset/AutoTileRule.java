package com.bytesmyth.graphics.tileset;

import java.util.ArrayList;

public class AutoTileRule {
    private TileDirection[] required;
    private TileDirection[] forbidden;
    private String variant;

    public AutoTileRule(int requiredMask, int forbiddenMask, String variant) {
        this.variant = variant;

        ArrayList<TileDirection> require = new ArrayList<>();
        ArrayList<TileDirection> forbid = new ArrayList<>();

        for (TileDirection dir : TileDirection.values()) {
            if ((requiredMask & dir.bitmask()) != 0) require.add(dir);
            if ((forbiddenMask & dir.bitmask()) != 0) forbid.add(dir);
        }

        required = require.toArray(new TileDirection[]{});
        forbidden = forbid.toArray(new TileDirection[]{});
    }

    public AutoTileRule(TileDirection[] required, TileDirection[] forbidden, String variant) {
        this.required = required;
        this.forbidden = forbidden;
        this.variant = variant;
    }

    public AutoTileRule() {
    }

    public int getForbidden() {
        int f = 0;
        for (TileDirection dir : forbidden) {
            f |= dir.bitmask();
        }
        return f;
    }

    public int getRequired() {
        int r = 0;
        for (TileDirection dir : required) {
            r |= dir.bitmask();
        }
        return r;
    }

    public String getVariant() {
        return variant;
    }
}
