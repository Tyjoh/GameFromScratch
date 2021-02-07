package com.bytesmyth.lifegame;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.bytesmyth.application.Game;
import com.bytesmyth.application.GameContext;
import com.bytesmyth.graphics.Graphics;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.graphics.texture.TextureRegion;
import com.bytesmyth.graphics.tileset.*;
import com.bytesmyth.graphics.ui.GuiManager;
import com.bytesmyth.lifegame.control.*;
import com.bytesmyth.lifegame.domain.building.Building;
import com.bytesmyth.lifegame.domain.partition.SpatialPartition;
import com.bytesmyth.lifegame.ecs.components.TransformComponent;
import com.bytesmyth.lifegame.tilemap.Chunk;
import com.bytesmyth.lifegame.tilemap.TileMap;
import com.bytesmyth.lifegame.tilemap.TileMapLayer;
import com.bytesmyth.lifegame.tilemap.TileMapRenderer;
import com.bytesmyth.lifegame.ui.Guis;
import com.bytesmyth.lifegame.ui.InGameHud;
import com.bytesmyth.lifegame.ui.InventoryTransferGui;
import com.bytesmyth.lifegame.ui.PlayerInventoryUI;
import org.joml.Vector2f;

import static org.lwjgl.opengl.GL11.*;

public class LifeGame implements Game {

    private final GameContext context;

    private World world;
    private TileMap map;
    private GuiManager guiManager;
    private Controls controls;

    private TileMapRenderer tileMapRenderer;
    private Graphics worldGraphics;
    private Graphics uiGraphics;

    private int playerEntity;
    private SpriteRegistry spriteRegistry;
    private Tileset tileset;

    public LifeGame(GameContext context) {
        this.context = context;
    }

    public World getWorld() {
        return world;
    }

    public TileMap getMap() {
        return map;
    }

    public Controls getControls() {
        return controls;
    }

    public Graphics getWorldGraphics() {
        return worldGraphics;
    }

    public Graphics getUiGraphics() {
        return uiGraphics;
    }

    public Tileset getTileset() {
        return tileset;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public void init() {
        //initialize textures and sprite mapping system.
        tileset = Tilesets.load("/tilesets/ground_tiles.json");
        spriteRegistry = DefaultSpriteRegistry.create(tileset);

        //initialize world graphics rendering systems
        OrthographicCamera2D worldCamera = new OrthographicCamera2D();
        worldCamera.setPosition(new Vector2f(16, 16));
        worldCamera.writePrevTransform();
        worldGraphics = new Graphics(worldCamera, spriteRegistry, 28);

        //initialize ui graphics rendering systems
        OrthographicCamera2D uiCamera = new OrthographicCamera2D();
        uiCamera.setPosition(new Vector2f(0, 0));
        uiGraphics = new Graphics(uiCamera, spriteRegistry, 1024);

        KeyboardVectorControl movement = new KeyboardVectorControl();
        ActivationControl interact = new KeyboardActivationControl("F");
        ActivationControl inventoryControl = new KeyboardActivationControl("E");
        VectorControl lookDirection = new MouseDirectionVectorControl(worldGraphics::toCameraCoordinates, () ->
            world.getEntity(playerEntity).getComponent(TransformComponent.class).getPosition()
        );

        controls = new Controls(movement, lookDirection, interact, inventoryControl);

        //initialize gui
        guiManager = new GuiManager(uiGraphics);
        guiManager.registerGui(Guis.DEBUG_HUD,"debug", new InGameHud(this, context));
        guiManager.enableGui(Guis.DEBUG_HUD);

        guiManager.registerGui(Guis.PLAYER_INVENTORY, "player", new PlayerInventoryUI(5, 3));
        guiManager.registerGui(Guis.PLAYER_TRANSFER_INVENTORY, "player", new InventoryTransferGui(5, 3));

        ChunkGen chunkGen = new ChunkGen(tileset);

        map = new TileMap();
        Chunk chunk1 = chunkGen.blankGrassChunk(0, 0);
        Chunk chunk2 = chunkGen.blankGrassChunk(1, 0);
        Chunk chunk3 = chunkGen.blankGrassChunk(0, 1);
        Chunk chunk4 = chunkGen.blankGrassChunk(1, 1);
//        Chunk chunk2 = chunkGen.blankGrassChunk(1, 0);
//        Chunk chunk3 = chunkGen.blankGrassChunk(-1, 0);
//        Chunk chunk4 = chunkGen.blankGrassChunk(0, 1);
//        Chunk chunk5 = chunkGen.blankGrassChunk(0, -1);
        map.addChunk(chunk1);
        map.addChunk(chunk2);
        map.addChunk(chunk3);
        map.addChunk(chunk4);
//        map.addChunk(chunk5);

        chunkGen.addAutoTileDemo(32, 32, map);
        chunkGen.addAutoTileDemo(9, 23, map);
        chunkGen.rectangle(0, 1, 63, 63, 2, map);
        chunkGen.rectangle(2, 2, 5, 5, 2, map);

        TileMapLayer layer0 = map.getLayer("0");
        TileMapLayer layer1 = map.getLayer("1");
        TileMapLayer layer2 = map.getLayer("2");

        Tiler globalTiler = tileset.getCombinedTiler();

        for (Chunk chunk : map.getLoadedChunks()) {
            for (int y = 0; y < chunk.getSize(); y++) {
                for (int x = 0; x < chunk.getSize(); x++) {
                    int wx = chunk.localToTileX(x);
                    int wy = chunk.localToTileY(y);

                    TileDef tile = globalTiler.tile(wx, wy, layer0::getTileType);
                    if (tile != null) layer0.getTile(wx, wy).setTextureRegion(tile.getRegion()).setVariant(tile.getVariant());

                    tile = globalTiler.tile(wx, wy, layer1::getTileType);
                    if (tile != null) layer1.getTile(wx, wy).setTextureRegion(tile.getRegion()).setVariant(tile.getVariant());

                    tile = globalTiler.tile(wx, wy, layer2::getTileType);
                    if (tile != null) layer2.getTile(wx, wy).setTextureRegion(tile.getRegion()).setVariant(tile.getVariant());
                }
            }
        }

        TextureRegion[] doorTiles = {
                tileset.getTile(5, 12).getRegion(),
                tileset.getTile(6, 12).getRegion(),
                tileset.getTile(7, 12).getRegion(),

                tileset.getTile(5, 13).getRegion(),
                tileset.getTile(6, 13).getRegion(),
                tileset.getTile(7, 13).getRegion(),
        };

        Building building = new Building(7, 5, 2)
                .wallTheme(new NinePatchTileBuilder(tileset).topLeft(0, 11).buildTextureArray())
                .roofTheme(new NinePatchTileBuilder(tileset).topLeft(0, 7).buildTextureArray())
                .setPosition(36, 14)
                .setDoor(2, 0, doorTiles);
        building.addToMap(map);

        WorldConfiguration config = WorldConfig.createDefault();
        config.register(worldCamera);
        config.register(worldGraphics);
        config.register(map);
        config.register(context);
        config.register(guiManager);
        config.register(this);
        config.register(controls);
        world = new World(config);

        tileMapRenderer = new TileMapRenderer(worldGraphics, tileset.getTexture());

        Texture characterTexture = new Texture("/textures/main_character.png");
        TextureAtlas characterAtlas = new TextureAtlas(characterTexture, 16, 16);
        CharacterFactory characterFactory = new CharacterFactory(world, characterAtlas);
        this.playerEntity = characterFactory.create(16, 16);

        new ChestFactory(this).create(16, 8);

        new VegetableFactory(this).create(20, 5);
        new VegetableFactory(this).create(21, 5);
        new VegetableFactory(this).create(22, 5);
        new VegetableFactory(this).create(23, 5);
        new VegetableFactory(this).create(20, 6);
        new VegetableFactory(this).create(21, 6);
        new VegetableFactory(this).create(22, 6);
        new VegetableFactory(this).create(23, 6);
    }

    @Override
    public void tick(float dt) {
        controls.tick(dt);
        guiManager.tick(dt);

        worldGraphics.getCamera().writePrevTransform();
        world.delta = dt;
        world.process();
        tileMapRenderer.render(map);
    }

    @Override
    public void render(float alpha) {
        controls.poll(context.getInput());
        guiManager.poll(context.getInput());

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        worldGraphics.getCamera().setAlpha(alpha);
        worldGraphics.render(alpha);
        guiManager.render();
    }

    @Override
    public void onWindowResized(int width, int height) {
        System.out.println("Screen size changed " + width + "," + height);
        worldGraphics.setScreenSize(width, height);
        uiGraphics.setScreenSize(width, height);
    }

    @Override
    public void dispose() {
        System.out.println("Shutting down");
    }

    public SpriteRegistry getSpriteRegistry() {
        return spriteRegistry;
    }

    public SpatialPartition getSpatialPartitioner() {
        return map;
    }
}
