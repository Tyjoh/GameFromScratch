package com.bytesmyth.graphics.tileset;

public enum TileDirection {
    NORTH_WEST(-1, 1, 1),
    NORTH(0, 1, 2),
    NORTH_EAST(1, 1, 4),
    EAST(1, 0, 8),
    SOUTH_EAST(1, -1, 16),
    SOUTH(0, -1, 32),
    SOUTH_WEST(-1, -1, 64),
    WEST(-1, 0, 128),
    ;

    private int dx;
    private int dy;
    private int bitmask;

    TileDirection(int dx, int dy, int bitmask) {
        this.dx = dx;
        this.dy = dy;
        this.bitmask = bitmask;
    }

    public int dx() {
        return dx;
    }

    public int dy() {
        return dy;
    }

    public int bitmask() {
        return bitmask;
    }
}
