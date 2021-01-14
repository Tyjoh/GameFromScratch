package com.bytesmyth.lifegame.tilemap;

import com.artemis.World;
import com.bytesmyth.graphics.texture.TextureRegion;
import com.bytesmyth.lifegame.ecs.components.FarmTileBehavior;
import com.bytesmyth.lifegame.ecs.components.TileEntity;

public class FarmTileRenderer implements TileRenderer {

    private World world;
    private TextureRegion growing;
    private TextureRegion mature;

    public FarmTileRenderer(World world, TextureRegion growing, TextureRegion mature) {
        this.world = world;
        this.growing = growing;
        this.mature = mature;
    }

    @Override
    public TextureRegion render(int worldX, int worldY, Tile tile) {
        TileEntity tileEntity = world.getEntity(tile.getEntityId()).getComponent(TileEntity.class);
        FarmTileBehavior farmTileBehavior = (FarmTileBehavior) tileEntity.getBehavior();
        if (farmTileBehavior.isMature()) {
            return mature;
        } else {
            return growing;
        }
    }
}
