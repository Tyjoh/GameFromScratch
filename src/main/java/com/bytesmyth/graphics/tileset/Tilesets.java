package com.bytesmyth.graphics.tileset;

import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.resources.Assets;
import com.google.gson.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Tilesets {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Tileset load(String file) {
        String tilesetFile = Assets.loadText(file);
        JsonObject root = JsonParser.parseString(tilesetFile).getAsJsonObject();

        String textureFile = root.get("texture").getAsString();
        int tileSize = root.get("tileSize").getAsInt();

        Texture texture = Assets.loadTexture(textureFile);
        TextureAtlas atlas = new TextureAtlas(texture, tileSize, tileSize);

        JsonArray defsJsonArray = root.getAsJsonArray("tileDefs");

        TileDefBuilder[] customDefBuilders = gson.fromJson(defsJsonArray, TileDefBuilder[].class);

        List<TileDef> customDefs = Arrays.stream(customDefBuilders).map(builder -> builder.build(atlas)).collect(Collectors.toList());

        Tileset tileset = new Tileset(atlas);
        tileset.addAll(customDefs);

        JsonArray autoTileDefJsonArray = root.getAsJsonArray("autoTileDefs");
        AutoTileDef[] autoTileDefs = gson.fromJson(autoTileDefJsonArray, AutoTileDef[].class);

        for (AutoTileDef autoTileDef : autoTileDefs) {
            AutoTiler tiler = AutoTileCompiler.compile(tileset, autoTileDef);
            tileset.addTiler(autoTileDef.type, tiler);
        }

        return tileset;
    }

    public static void save(String file, Tileset tileset) throws IOException {
        JsonObject root = new JsonObject();
        root.addProperty("texture", tileset.getTexture().getFile());
        root.addProperty("tileSize", tileset.getTileSize());
        root.addProperty("tileWidth", tileset.getTileSize());
        root.addProperty("tileHeight", tileset.getTileSize());

        List<TileDef> defs = tileset.getCustomDefs();

        List<TileDefBuilder> dtos = defs.stream().map(TileDefBuilder::new).collect(Collectors.toList());

        JsonElement defsJsonArray = gson.toJsonTree(dtos);
        root.add("tileDefs", defsJsonArray);

        String tilesetDescriptor = gson.toJson(root);
        Files.write(Paths.get(file), tilesetDescriptor.getBytes());
    }
}
