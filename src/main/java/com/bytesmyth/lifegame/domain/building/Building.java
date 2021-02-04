package com.bytesmyth.lifegame.domain.building;

import com.bytesmyth.graphics.texture.TextureRegion;
import com.bytesmyth.lifegame.tilemap.Tile;
import com.bytesmyth.lifegame.tilemap.TileMap;
import com.bytesmyth.lifegame.tilemap.TileMapLayer;

public class Building {

    private static final String FLOOR_LAYER = "0";
    private static final String BASE_LAYER = "1";
    private static final String GRAPHIC_LAYER = "2";
    private static final String COLLISION_LAYER = "collision";

    private int width; //x axis
    private int length; //y axis
    private int height; //pseudo z axis

    private int x;
    private int y;

    private TextureRegion[] roofTiles;
    private TextureRegion[] wallTiles;
    private int doorX;
    private int doorY;
    private TextureRegion[] doorTiles;

    public Building(int width, int length, int height) {
        if (width < 5) throw new IllegalArgumentException("Building must be > 4 tiles wide (x axis)");
        if (length < 3) throw new IllegalArgumentException("Building must be > 3 tiles long (y axis)");
        if (height < 2) throw new IllegalArgumentException("Building must be > 2 tiles tall (z axis)");

        this.width = width;
        this.length = length;
        this.height = height;
    }

    public Building setDoor(int dx, int dy, TextureRegion[] doorTiles) {
        this.doorX = dx;
        this.doorY = dy;
        this.doorTiles = doorTiles;
        return this;
    }

    public Building roofTheme(TextureRegion[] roofTiles) {
        this.roofTiles = roofTiles;
        return this;
    }

    public Building wallTheme(TextureRegion[] wallTiles) {
        this.wallTiles = wallTiles;
        return this;
    }

    public Building setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public void addToMap(TileMap map) {
        //mark tile types
        TileMapLayer floorLayer = map.getLayer(FLOOR_LAYER);//contains wall touching ground
        TileMapLayer baseLayer = map.getLayer(BASE_LAYER);//contains wall touching ground
        TileMapLayer graphicLayer = map.getLayer(GRAPHIC_LAYER); // every other layer
        TileMapLayer collisionLayer = map.getLayer(COLLISION_LAYER); // every other layer

        addWallToMap(graphicLayer, collisionLayer);
        tileWall(graphicLayer);

        addRoofToMap(graphicLayer, collisionLayer);
        tileRoof(graphicLayer);

        addDoorToMap(graphicLayer);

        shiftBaseToPlayerLayer(baseLayer, graphicLayer);
        shiftDoorToFloor(floorLayer, baseLayer, collisionLayer);
    }


    private void addDoorToMap(TileMapLayer graphicLayer) {
        graphicLayer.getTile(this.x + doorX - 1, this.y + 1).setTextureRegion(doorTiles[0]);
        graphicLayer.getTile(this.x + doorX, this.y + 1).setTextureRegion(doorTiles[1]);
        graphicLayer.getTile(this.x + doorX + 1, this.y + 1).setTextureRegion(doorTiles[2]);

        graphicLayer.getTile(this.x + doorX - 1, this.y).setTextureRegion(doorTiles[3]);
        graphicLayer.getTile(this.x + doorX, this.y).setTextureRegion(doorTiles[4]);
        graphicLayer.getTile(this.x + doorX + 1, this.y).setTextureRegion(doorTiles[5]);
    }

    private void shiftDoorToFloor(TileMapLayer floor, TileMapLayer base, TileMapLayer collision) {
        Tile doorTile = base.getTile(this.x + this.doorX, this.y + this.doorY);
        base.setTile(this.x + this.doorX, this.y + this.doorY, null);
        floor.setTile(this.x + this.doorX, this.y + this.doorY, doorTile);
        collision.setTile(this.x + this.doorX, this.y + this.doorY, null);
        collision.setTile(this.x + this.doorX, this.y + this.doorY + 1, null);
    }

    private void shiftBaseToPlayerLayer(TileMapLayer baseLayer, TileMapLayer graphicLayer) {
        for (int x = 0; x < width; x++) {
            Tile tile = graphicLayer.getTile(this.x + x, this.y);
            graphicLayer.setTile(this.x + x, this.y, null);
            baseLayer.setTile(this.x + x, this.y, tile);
        }
    }

    private void tileRoof(TileMapLayer graphicLayer) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < length; y++) {
                int id = kern(graphicLayer, "building_roof", this.x + x, this.y + y + height);
                graphicLayer.getTile(this.x + x, this.y + y + height).setTextureRegion(roofTiles[id]);
            }
        }
    }

    private void tileWall(TileMapLayer graphicLayer) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int id = kern(graphicLayer, "building_wall", this.x + x, this.y + y);
                graphicLayer.getTile(this.x + x, this.y + y).setTextureRegion(wallTiles[id]);
            }
        }
    }

    private void addRoofToMap(TileMapLayer graphicLayer, TileMapLayer collisionLayer) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < length; y++) {
                graphicLayer.setTile(this.x + x, height + this.y + y, new Tile("building_roof"));
                collisionLayer.setTile(this.x + x, height + this.y + y, new Tile("solid"));
            }
        }
    }

    private void addWallToMap(TileMapLayer graphicLayer, TileMapLayer collisionLayer) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                graphicLayer.setTile(this.x + x, this.y + y, new Tile("building_wall"));
                collisionLayer.setTile(this.x + x, this.y + y, new Tile("solid"));
            }
        }
    }

    private int kern(TileMapLayer layer, String type, int x, int y) {
        int tile = 0;

        tile += isTile(layer.getTile(x, y + 1), type) ? 1 : 0;
        tile += isTile(layer.getTile(x + 1, y), type) ? 2 : 0;
        tile += isTile(layer.getTile(x, y - 1), type) ? 4 : 0;
        tile += isTile(layer.getTile(x - 1, y), type) ? 8 : 0;

        return tile;
    }

    private boolean isTile(Tile tile, String type) {
        if (tile == null) return false;
        return tile.getType().equals(type);
    }

}
