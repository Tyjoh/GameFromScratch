package com.bytesmyth.testgame.tilemap;

import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.testgame.tilemap.tools.Prefab;

import java.util.HashMap;
import java.util.Map;

public class VillageTileMapTools {

    private Map<String, Prefab> prefabs = new HashMap<>();

    public VillageTileMapTools(TextureAtlas atlas) {
        Prefab red_roof = new Prefab("red_roof");
        red_roof.addRect("object2", 4, 6, 8, 8, atlas, 0, 1);//roof
        red_roof.solidify();
        addPrefab(red_roof);

        Prefab wood_house = new Prefab("wood_house");
        wood_house.addRect("object1", 4, 4, 8, 6, atlas, 0, 0);//base
        wood_house.add("object2", atlas.tileCoordToId(8, 5), 1, 0);//door
        wood_house.add("object2", atlas.tileCoordToId(8, 4), 2, 0);//window
        wood_house.solidify();
        addPrefab(wood_house);


        Prefab blue_roof = new Prefab("blue_roof");
        blue_roof.addRect("object2", 0, 6, 4, 8, atlas, 0, 1);
        blue_roof.solidify();
        addPrefab(blue_roof);

        Prefab stone_house = new Prefab("stone_house");
        stone_house.addRect("object1", 0, 4, 4, 6, atlas, 0, 0);
        stone_house.add("object2", atlas.tileCoordToId(8, 5), 1, 0);
        stone_house.add("object2", atlas.tileCoordToId(8, 4), 2, 0);
        stone_house.solidify();
        this.addPrefab(stone_house);

        addPrefab(Prefab.merge("red_wood_house", red_roof, wood_house));
        addPrefab(Prefab.merge("red_stone_house", red_roof, stone_house));
        addPrefab(Prefab.merge("blue_wood_house", blue_roof, wood_house));
        addPrefab(Prefab.merge("blue_stone_house", blue_roof, stone_house));

        Prefab rock1 = new Prefab("rock1");
        rock1.add("object1", atlas.tileCoordToId(17, 3), 0, 0);
        rock1.solidify();
        this.addPrefab(rock1);
    }


    public Prefab getPrefab(String tool) {
        return prefabs.get(tool);
    }

    private void addPrefab(Prefab tool) {
        prefabs.put(tool.getName(), tool);
    }

}
