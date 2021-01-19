package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Component;
import com.bytesmyth.lifegame.domain.tile.TileLogic;

public class TileComponent extends Component {
    private String layer;
    private int x;
    private int y;

    private TileLogic behavior = (w, e) -> {};

    public TileComponent() {

    }

    public TileComponent(String layer, int x, int y) {
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

    public TileLogic getBehavior() {
        return behavior;
    }

    public void setBehavior(TileLogic behavior) {
        this.behavior = behavior;
    }
}
