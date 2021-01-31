package com.bytesmyth.lifegame;

import com.bytesmyth.lifegame.tilemap.Tile;
import com.bytesmyth.lifegame.tilemap.TileMap;
import com.bytesmyth.lifegame.tilemap.TileMapLayer;

public class TestMapGen {

    private final LifeGame game;

    public TestMapGen(LifeGame game) {
        this.game = game;
    }

    public TileMap newMap() {
        TileMap map = new TileMap(64);
        SpriteRegistry spriteRegistry = game.getSpriteRegistry();
        TileMapLayer ground = map.createLayer("ground");
        map.createLayer("collision");
        map.createLayer("1");
        map.createLayer("2");

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
//                ground.setTile(x, y, new Tile("grass").setSprite(spriteRegistry.getSprite("grass")));
            }
        }

        return map;
    }

    public void addRandomRocks(int count) {
        TileMap map = game.getMap();
        SpriteRegistry spriteRegistry = game.getSpriteRegistry();

        TileMapLayer collision = map.getLayer("collision");
        TileMapLayer object1 = map.getLayer("1");

        for (int i = 0; i < count; i++) {
            int x = (int) (Math.random() * 64);
            int y = (int) (Math.random() * 64);
//            object1.setTile(x, y, new Tile("rock").setSprite(spriteRegistry.getSprite("rock")));
            collision.setTile(x, y, new Tile("solid"));
        }
    }

    public void addRandomCoins(int count) {
        CoinFactory coinFactory = new CoinFactory(game);
        for (int i = 0; i < count; i++) {
            coinFactory.create((float) Math.random() * 64, (float) Math.random() * 64);
        }
    }

    public void addRandomBushes(int count, float spreadFactor) {
        TileMap map = game.getMap();
        TileMapLayer object1 = map.getLayer("1");

        VegetableFactory vegtableFactory = new VegetableFactory(game);

        //seed bushes
        for (int i = 0; i < count; i++) {
            vegtableFactory.create((int) (Math.random() * map.getWidth()), (int) (Math.random() * map.getHeight()));
        }

        //expand bushes
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {

                Tile left = object1.getTile(x - 1, y);
                Tile right = object1.getTile(x + 1, y);
                Tile below = object1.getTile(x, y - 1);
                Tile above = object1.getTile(x, y + 1);
                boolean adjacentBush = left != null && left.getType().equals("bush")
                                || right != null && right.getType().equals("bush")
                                || above != null && above.getType().equals("bush")
                                || below != null && below.getType().equals("bush");

                if (adjacentBush && Math.random() < spreadFactor) {
                    vegtableFactory.create(x, y);
                }

            }
        }
    }

}
