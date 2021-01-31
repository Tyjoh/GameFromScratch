package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Component;
import com.bytesmyth.lifegame.domain.tile.TileLogic;

public class TileComponent extends Component {
    private String layer;
    private String type;
    private int x;
    private int y;

    private boolean isSolid;

    private TileLogic behavior = (w, e) -> {};

    public TileComponent() {

    }

    public TileComponent(String layer, String type, int x, int y) {
        this.layer = layer;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public boolean isSolid() {
        return isSolid;
    }



    public TileLogic getBehavior() {
        return behavior;
    }

    public void setBehavior(TileLogic behavior) {
        this.behavior = behavior;
    }
}
