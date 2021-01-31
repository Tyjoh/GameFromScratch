package com.bytesmyth.lifegame;

import com.artemis.Entity;
import com.artemis.World;
import com.bytesmyth.graphics.texture.TextureRegion;
import com.bytesmyth.lifegame.domain.interaction.FarmInteractionHandler;
import com.bytesmyth.lifegame.domain.tile.FarmTileLogic;
import com.bytesmyth.lifegame.ecs.components.InteractiveComponent;
import com.bytesmyth.lifegame.ecs.components.TileComponent;
import com.bytesmyth.lifegame.ecs.components.TileGraphicsComponent;
import com.bytesmyth.lifegame.tilemap.Tile;
import com.bytesmyth.lifegame.tilemap.TileMap;
import com.bytesmyth.lifegame.tilemap.TileRegistry;
import com.bytesmyth.util.Provider;

public class VegetableFactory {
    private final World world;
    private final TileMap map;
    private final TileRegistry tileRegistry;

    public VegetableFactory(LifeGame game) {
        this.world = game.getWorld();
        this.map = game.getMap();
        this.tileRegistry = game.getTileRegistry();
    }

    public void create(int x, int y) {

        Entity entity = world.createEntity();
        map.getLayer("1").setTile(x, y, new Tile("vegetable").setDynamicEntityId(entity.getId()));
//        map.getLayer("collision").setTile(x, y, new Tile("solid").setDynamicEntityId(entity.getId()));

        TileComponent tileComponent = new TileComponent("1", "vegetable", x, y);
        FarmTileLogic farmTileLogic = new FarmTileLogic("vegetable");
        tileComponent.setBehavior(farmTileLogic);

        GrowthTileGraphics graphics = new GrowthTileGraphics(farmTileLogic, new TextureRegion[]{
                tileRegistry.getRegion("vegetable", "growth_1"),
                tileRegistry.getRegion("vegetable", "growth_2"),
                tileRegistry.getRegion("vegetable", "growth_3"),
                tileRegistry.getRegion("vegetable", "growth_4")
        });

        entity.edit().add(tileComponent)
                .add(new InteractiveComponent(new FarmInteractionHandler(entity)))
                .add(new TileGraphicsComponent().setTextureProvider(graphics));
    }

    private static class GrowthTileGraphics implements Provider<TextureRegion> {

        private FarmTileLogic farm;
        private TextureRegion[] stages;

        private GrowthTileGraphics(FarmTileLogic farm, TextureRegion[] stages) {
            this.farm = farm;
            this.stages = stages;
        }

        @Override
        public TextureRegion get() {
            if (farm.getAge() < farm.getMatureAge()) {
                float percent = farm.getAge() / (float) farm.getMatureAge();
                int stage = (int) ((stages.length - 1) * percent);
                return stages[stage];
            } else {
                return stages[stages.length - 1];
            }
        }
    }
}
