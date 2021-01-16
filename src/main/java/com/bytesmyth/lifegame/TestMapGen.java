package com.bytesmyth.lifegame;

import com.artemis.World;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.lifegame.tilemap.Tile;
import com.bytesmyth.lifegame.tilemap.TileMap;
import com.bytesmyth.lifegame.tilemap.TileMapLayer;

public class TestMapGen {

    public TileMap newMap() {
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

    public void addRandomRocks(TileMap map, int count) {
        TileMapLayer collision = map.getLayer("collision");
        TileMapLayer object1 = map.getLayer("1");

        for (int i = 0; i < count; i++) {
            int x = (int) (Math.random() * 64);
            int y = (int) (Math.random() * 64);
            object1.setTile(x, y, new Tile("rock"));
            collision.setTile(x, y, new Tile("solid"));
        }
    }

    public void addRandomCoins(World world, TextureAtlas atlas, int count) {
        CoinFactory coinFactory = new CoinFactory(world, atlas);
        for (int i = 0; i < count; i++) {
            coinFactory.create((float) Math.random() * 64, (float) Math.random() * 64);
        }
    }

    public void addRandomBushes(TileMap map, World world, int count, float spreadFactor) {
        TileMapLayer object1 = map.getLayer("1");

        BushFactory bushFactory = new BushFactory(world, map);

        //seed bushes
        for (int i = 0; i < count; i++) {
            bushFactory.create((int) (Math.random() * map.getWidth()), (int) (Math.random() * map.getHeight()));
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

                if (adjacentBush && Math.random() < spreadFactor) {
                    bushFactory.create(x, y);
                }

            }
        }
    }

}
