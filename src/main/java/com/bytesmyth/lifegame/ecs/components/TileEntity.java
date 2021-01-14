package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Component;

public class TileEntity extends Component {
    private String layer;
    private int x;
    private int y;

    private TileBehavior behavior = (w, e) -> {};

    public TileEntity() {

    }

    public TileEntity(String layer, int x, int y) {
        this.layer = layer;
        this.x = x;
        this.y = y;
    }

    public String getLayer() {
        return layer;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TileBehavior getBehavior() {
        return behavior;
    }

    public void setBehavior(TileBehavior behavior) {
        this.behavior = behavior;
    }
}
