package com.bytesmyth.lifegame;

import com.bytesmyth.graphics.tileset.Tileset;
import com.bytesmyth.lifegame.tilemap.*;

public class ChunkGen {

    private final Tileset tileset;

    public ChunkGen(Tileset tileset) {
        this.tileset = tileset;
    }

    public MapChunk blankGrassChunk(int chunkX, int chunkY) {
        MapChunk chunk = new MapChunk(chunkX, chunkY);

        ChunkLayer layer0 = chunk.createLayer("0");
        chunk.createLayer("1");
        chunk.createLayer("2");
        chunk.createLayer("3");
        chunk.createLayer("4");
        chunk.createLayer("5");
        chunk.createLayer("6");
        chunk.createLayer("7");
        chunk.createLayer("collision");

        for (int y = 0; y < chunk.getSize(); y++) {
            for (int x = 0; x < chunk.getSize(); x++) {
                Tile tile;

                double random = Math.random();

                if (random > 0.995f) {
                    tile = texturedTile("grass", "3");
                } else if (random > 0.97f) {
                    tile = texturedTile("grass", "2");
                } else if (random > 0.94f) {
                    tile = texturedTile("grass", "1");
                } else {
                    tile = texturedTile("grass", "0");
                }

                layer0.setTile(x, y, tile);
            }
        }

        expandPatch(layer0,"grass", "3", "2");
        expandPatch(layer0,"grass", "2", "1");

        return chunk;
    }

    public void addAutoTileDemo(int cx, int cy, TileMap map) {
        TileMapLayer layer2 = map.getLayer("2");

        for (int y = cy - 7; y < cy + 8; y++) {
            for (int x = cx - 2; x < cx + 3; x++) {
                layer2.setTile(x, y, new Tile("top_grass"));
            }
        }

        for (int y = cy - 4; y < cy + 5; y++) {
            for (int x = cx - 5; x < cx + 6; x++) {
                layer2.setTile(x, y, new Tile("top_grass"));
            }
        }


        for (int x = cx - 7; x < cx + 8; x++) {
            for (int y = cy - 2; y < cy + 3; y++) {
                layer2.setTile(x, y, new Tile("top_grass"));
            }
        }

        for (int y = cy - 5; y < cy + 6; y++) {
            for (int x = cx - 4; x < cx + 5; x++) {
                layer2.setTile(x, y, new Tile("top_grass"));
            }
        }

        for (int y = cy - 3; y < cy + 4; y++) {
            for (int x = cx - 3; x < cx + 4; x++) {
                int dx = cx - x;
                int dy = cy - y;

                if (Math.sqrt((dx*dx) + (dy*dy)) <= 3.2f) {
                    layer2.setTile(x, y, null);
                }
            }
        }

        addDirtAndCollision(map);
    }

    private void addDirtAndCollision(TileMap map) {
//        TileMapLayer layer2 = map.getLayer("2");

        for (MapChunk chunk : map.getLoadedChunks()) {
            for (int y = 0; y < chunk.getSize(); y++) {
                for (int x = 0; x < chunk.getSize(); x++) {
                    ChunkLayer layer2 = chunk.getLayer("2");
                    Tile tile = layer2.getTile(x, y);
                    Tile top = layer2.getTile(x, y + 1);
                    Tile right = layer2.getTile(x + 1, y);
                    Tile bottom = layer2.getTile(x, y - 1);
                    Tile left = layer2.getTile(x - 1, y);

                    if (is(tile, "top_grass") || is(top, "top_grass")) {
                        chunk.getLayer("collision").setTile(x, y, new Tile("solid"));
                        chunk.getLayer("1").setTile(x, y, new Tile("dirt_wall"));
                    }

                    if (tile == null && (is(top, "top_grass") || is(right, "top_grass") || is(bottom, "top_grass") || is(left, "top_grass"))) {
                        chunk.getLayer("2").setTile(x, y, new Tile("top_grass_edge"));
                    }
                }
            }
        }
    }

    private boolean is(Tile tile, String type) {
        return tile != null && tile.getType().equals(type);
    }

    private void expandPatch(ChunkLayer layer, String type, String targetVariant, String surroundVariant) {
        for (int y = 0; y < layer.getHeight(); y++) {
            for (int x = 0; x < layer.getWidth(); x++) {
                Tile tile = layer.getTile(x, y);
                if (tile.getType().equals(type) && targetVariant.equals(tile.getVariant())) {
                    replaceIfDefaultVariant(layer,x - 1, y, type, surroundVariant);
                    replaceIfDefaultVariant(layer,x + 1, y, type, surroundVariant);
                    replaceIfDefaultVariant(layer,x, y + 1, type, surroundVariant);
                    replaceIfDefaultVariant(layer,x, y - 1, type, surroundVariant);
                }
            }
        }
    }

    private void replaceIfDefaultVariant(ChunkLayer layer, int x, int y, String type, String variant) {
        Tile tile = layer.getTile(x, y);
        if (tile != null && tile.getType().equals(type) && tile.getVariant().equals("0")) {
            layer.setTile(x, y, texturedTile(type, variant));
        }
    }

    private Tile texturedTile(String type, String variant) {
        return new Tile(type, variant).setTextureRegion(tileset.getTile(type, variant).getRegion());
    }

    public void rectangle(int x1, int y1, int x2, int y2, int thickness, TileMap map) {
        TileMapLayer layer2 = map.getLayer("2");

        for (int thick = 0; thick < thickness; thick++) {
            for (int y = y1; y <= y2; y++) {
                layer2.setTile(x1 + thick, y, new Tile("top_grass"));
                layer2.setTile(x2 - thick, y, new Tile("top_grass"));
            }

            for (int x = x1; x <= x2; x++) {
                layer2.setTile(x, y1 + thick, new Tile("top_grass"));
                layer2.setTile(x, y2 - thick, new Tile("top_grass"));
            }
        }

        addDirtAndCollision(map);
    }
}
