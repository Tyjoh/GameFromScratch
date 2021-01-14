package com.bytesmyth.lifegame;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.Entity;
import com.artemis.World;
import com.bytesmyth.lifegame.ecs.components.FarmTileBehavior;
import com.bytesmyth.lifegame.ecs.components.TileEntity;
import com.bytesmyth.lifegame.tilemap.Tile;
import com.bytesmyth.lifegame.tilemap.TileMap;
import com.bytesmyth.lifegame.tilemap.TileMapLayer;

public class TestMapGen {

    public TileMap genMap() {
        TileMap map = new TileMap(64);
        TileMapLayer ground = map.createLayer("ground");
        TileMapLayer collision = map.createLayer("collision");
        TileMapLayer object1 = map.createLayer("1");
        TileMapLayer object2 = map.createLayer("2");

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                ground.setTile(x, y, new Tile("grass"));

                float v = (float) Math.random();

                if (v < 0.02) {
                    object1.setTile(x, y, new Tile("rock"));
                    collision.setTile(x, y, new Tile("solid"));
                }
            }
        }

        return map;
    }

    public void addRandomBushes(TileMap map, World world) {
        TileMapLayer object1 = map.getLayer("1");

        //seed bushes
        for (int i = 0; i < 30; i++) {
            createBush(world, map, (int) (Math.random() * map.getWidth()), (int) (Math.random() * map.getHeight()));
        }

        //expand bushes
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {

                Tile left = object1.getTile(x - 1, y);
                Tile right = object1.getTile(x + 1, y);
                Tile below = object1.getTile(x, y - 1);
                Tile above = object1.getTile(x, y + 1);
                boolean adjacentBush = left != null && left.getId().equals("bush")
                                || right != null && right.getId().equals("bush")
                                || above != null && above.getId().equals("bush")
                                || below != null && below.getId().equals("bush");

                if (adjacentBush && Math.random() < 0.4) {
                    createBush(world, map, x, y);
                }

            }
        }
    }

    public void createBush(World world, TileMap map, int x, int y) {
        Tile bushTile = new Tile("bush");
        map.getLayer("1").setTile(x, y, bushTile);
        map.getLayer("collision").setTile(x, y, new Tile("solid"));

        Entity entity = world.createEntity();
        bushTile.setDynamicEntityId(entity.getId());
        TileEntity tileEntity = new TileEntity("1", x, y);
        tileEntity.setBehavior(new FarmTileBehavior((int) (500 + Math.random() * 3000)));
        entity.edit().add(tileEntity);
    }

}
